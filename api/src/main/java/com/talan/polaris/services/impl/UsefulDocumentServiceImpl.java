package com.talan.polaris.services.impl;

import com.talan.polaris.Application;
import com.talan.polaris.dto.ImagePDFDTO;
import com.talan.polaris.entities.UsefulDocumentEntity;
import com.talan.polaris.repositories.UsefulDocumentRepository;
import com.talan.polaris.services.EDMService;
import com.talan.polaris.services.UsefulDocumentService;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

@Service
public class UsefulDocumentServiceImpl implements UsefulDocumentService {
    private final UsefulDocumentRepository usefulDocumentRepository;
    private final EDMService edmService;
    private static final String DEFAULT_SORT_FIELD_NAME = "createdAt";
    private static final Logger LOGGER = LoggerFactory.getLogger(UsefulDocumentServiceImpl.class);
    @Autowired
    public UsefulDocumentServiceImpl(UsefulDocumentRepository repository,
                                     EDMService edmService) {
        this.usefulDocumentRepository = repository;
        this.edmService = edmService;
    }

    @Override
    public Collection<UsefulDocumentEntity> findAllUsefulDocument() {

        return  this.usefulDocumentRepository.findAll(Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD_NAME));    }

    @Override
    public UsefulDocumentEntity update(UsefulDocumentEntity usefulDocumentEntity) throws IOException {
        return this.usefulDocumentRepository.saveAndFlush(usefulDocumentEntity);
    }
    @Override
    public void generateImageFromPDF(UsefulDocumentEntity usefulDocumentEntity) throws IOException {
        LOGGER.info("Generate Image From PDF start");
        File sourceFile =  this.convertBytesToFile(usefulDocumentEntity.getIdEDM());
        String sourceDir = sourceFile.getAbsolutePath();
        LOGGER.info("get the path of file {}", sourceDir);
        byte[] firstPageAsImagebytes = null;
        if (sourceFile.exists()) {
            LOGGER.info("Source file exist");
           PDDocument document = PDDocument.load(sourceDir);
            List<PDPage> list = document.getDocumentCatalog().getAllPages();
            BufferedImage image = list.get(0).convertToImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ImageIO.write(image, "png", baos);
            firstPageAsImagebytes = baos.toByteArray();
            usefulDocumentEntity.setThumbnail(firstPageAsImagebytes);
            usefulDocumentRepository.saveAndFlush(usefulDocumentEntity);
            document.close();
            deleteLocalFile(sourceDir);
        }
       //  return firstPageAsImagebytes;
    }
    public void deleteLocalFile( String filePath) {
             Path path = FileSystems.getDefault().getPath(filePath);
            try {
                Files.delete(path);
                LOGGER.info("deleted {}", path);
            } catch (NoSuchFileException x) {
                LOGGER.error("%s: no such" + " file or directory%n", path);
            } catch (IOException x) {
                LOGGER.error(x.getMessage());
            }

    }
    @Override
    public File convertBytesToFile(Long idEDM) throws IOException {
        byte[] templateBytes = edmService.downloadFileFromEDM(idEDM);
        File file = createFiles();
        FileUtils.writeByteArrayToFile(file, templateBytes);
        return file;
    }
    public File createFiles() {
        String dir = Application.class.getResource("/").getFile();
        String path = dir + "file" + generateRandomNumber() + ".pdf";
        return new File(path);
    }
    public int generateRandomNumber() {
        return new Random().nextInt(5000);
    }

    @Override
    public void deleteImage(UsefulDocumentEntity usefulDocumentEntity) {
        byte[] val = null;
       usefulDocumentEntity.setThumbnail(val);
       usefulDocumentRepository.saveAndFlush(usefulDocumentEntity);
    }



    @Override
    public Collection<ImagePDFDTO> getListOfImage(){
        Collection<ImagePDFDTO> imageList= new ArrayList<>();
        Collection<UsefulDocumentEntity> usefulDocumentList = findAllUsefulDocument();
        for(UsefulDocumentEntity usefulDocument : usefulDocumentList){
            ImagePDFDTO imagePDFDTO = new ImagePDFDTO();
            if(usefulDocument.getThumbnail()!=null && usefulDocument.getIdEDM()!=null ) {
                String prefix = "data:image/png;base64,";
                imagePDFDTO.setThumbnail(prefix+Base64.getEncoder().encodeToString(usefulDocument.getThumbnail()));
                imagePDFDTO.setIdEDM(usefulDocument.getIdEDM());
                imagePDFDTO.setLabel(usefulDocument.getLabel());
                imageList.add(imagePDFDTO);
            }
        }
        return imageList;
    }
}


