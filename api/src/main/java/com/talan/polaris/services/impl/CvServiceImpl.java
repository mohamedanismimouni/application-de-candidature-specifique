package com.talan.polaris.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.polaris.entities.CvEntity;
import com.talan.polaris.repositories.CvRepository;
import com.talan.polaris.services.CvService;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class CvServiceImpl implements CvService {
    private static final Logger log = LoggerFactory.getLogger(CvServiceImpl.class);

    private static final String[] VALID_FILES_EXTENSION = {"pdf", "doc", "docx"};
    private static final String INDEX_NAME = "cv";
    private static final String FOLDER_PATH = "cv";
    private final Path rootPath = Paths.get(FOLDER_PATH);

    @Autowired
    private CvRepository cvRepository;

    @Autowired
    private RestHighLevelClient highLevelClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String,Object> save(MultipartFile file) throws IOException, IllegalArgumentException {
        String encoded = new String(Base64.getEncoder().encodeToString(file.getBytes()));

        String pathCv=file.getOriginalFilename();

        String extension = getFileExtension(file.getOriginalFilename());

        if (!isValidFileExtension(extension)) {
            log.error("Save CV - Bad extension - " + extension);
            throw new IllegalArgumentException("Invalid extension: " + extension);
        }
        //Converting Bit file to String

        String contents = Base64.getEncoder().encodeToString(file.getBytes());

        //Index the file by its content with the attachment pipeline
        IndexRequest request = new IndexRequest(INDEX_NAME)

                .source("data",contents)
                .setPipeline("attachment");
        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
        CvEntity cv=cvRepository.findById(response.getId()).get();
        HashSet<String> skills = ListSkills(cv.getContent());
        HashSet<String> emails = getEmailFromCv(cv.getContent());
        String phone = AnalyzeTextGetPhone(cv.getContent());
        String idCv = cv.getId();
        HashMap<String,Object> map = new HashMap<>();
        map.put("id_cv",idCv);
        map.put("skills",skills);
        map.put("emails",emails);
        map.put("phone",phone);
        return map;
    }

    @Override
    public List<CvEntity> search(String keyword) {
        return cvRepository.search(keyword);
    }
    public HashSet<String> getEmailFromCv (String content ) throws  IOException{
        HashSet<String> emails = new HashSet<>();
        AnalyzeRequest request = AnalyzeRequest.buildCustomAnalyzer("uax_url_email")
                .addTokenFilter("lowercase")
                .build(content);
        AnalyzeResponse response = highLevelClient.indices().analyze(request,RequestOptions.DEFAULT);
        List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
        for(AnalyzeResponse.AnalyzeToken a:tokens) {
            String term= a.getTerm();
            if (term.contains("@")){
            emails.add(term);
            }
        }
        return emails;
    }

    @Override
    public String AnalyzeTextGetPhone(String content) throws IOException{
        String phone="";
        content=String.join("", content.split(" "));
        Map<String,Object> tokenizerSettings = new HashMap<String,Object>();
        tokenizerSettings.put("type", "simple_pattern");
        tokenizerSettings.put("pattern","[0123456789]{8,}|\\+[0123456789]{8,}");
        AnalyzeRequest request = AnalyzeRequest.buildCustomAnalyzer(tokenizerSettings)
                .addTokenFilter("lowercase")
                .build(content);
        AnalyzeResponse response = highLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
        if (!response.getTokens().isEmpty()) {
            phone=response.getTokens().get(0).getTerm();
        }
        return phone;
    }

    public HashSet<String> ListSkills (String content ) throws  IOException{
        HashSet <String> skills = new HashSet<>()  ;
        Map<String, Object> keepFilter = new HashMap<>();
        keepFilter.put("type", "keep");
        ArrayList<String> skill= new ArrayList<>(Arrays.asList("java","html","css","javascript","c++","angular"
                ,"c","docker","elasticsearch","git","github","kafka","javascript","java script","bootstrap","php"));
        keepFilter.put("keep_words", skill);
        Map<String,Object> charGroupTokenizer = new HashMap<>();
        charGroupTokenizer.put("type", "char_group");
        ArrayList<String> separators = new ArrayList<>(Arrays.asList("whitespace" , "-" , "\n","/",",","0","1","2","3","4","5","6","7","8","9"));
        charGroupTokenizer.put("tokenize_on_chars",separators);
        AnalyzeRequest request = AnalyzeRequest.buildCustomAnalyzer(charGroupTokenizer)
                .addTokenFilter("lowercase")
                .addTokenFilter(keepFilter)
                .build(content);
        AnalyzeResponse response = highLevelClient.indices().analyze(request,RequestOptions.DEFAULT);
        List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
        for(AnalyzeResponse.AnalyzeToken a:tokens) {
            String term= a.getTerm();
            skills.add(term);
        }
        return skills;
    }

    private static String getFileExtension(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            return filename.substring(i + 1);
        }

        return "";
    }
    private static boolean isValidFileExtension(String extension) {
        for (String ext : VALID_FILES_EXTENSION) {
            if (ext.equals(extension)) {
                return true;
            }
        }

        return false;
    }
    @Override
    public CvEntity getCvById(String id){
        Optional<CvEntity> cv = cvRepository.findById(id);
        if (cv.isPresent()){
            return cv.get();
        }else {
            return null;
        }
    }








}
