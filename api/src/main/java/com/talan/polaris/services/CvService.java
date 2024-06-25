package com.talan.polaris.services;

import com.talan.polaris.entities.CvEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CvService {

    Map<String,Object> save(MultipartFile file) throws IOException, IllegalArgumentException;

    List<CvEntity> search(String keyword);

    CvEntity getCvById (String id);

    String AnalyzeTextGetPhone(String content) throws IOException;

}
