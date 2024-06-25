package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.web.multipart.MultipartFile;


@Document(indexName = "cv", createIndex = true)
public class CvEntity {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "path")
    private String path;

    @Field(type = FieldType.Text, name = "data")
    @JsonIgnore
    private String encoded;

    @Field(type = FieldType.Text, name = "attachment.content")
    private String content;

    
    public CvEntity() { }

    public String getId() {
        return id;
    }

    public String getEncoded() {
        return encoded;
    }

    public String getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }



    
}
