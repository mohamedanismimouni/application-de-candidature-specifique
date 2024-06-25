package com.talan.polaris.controllers;

import com.talan.polaris.Application;
import com.talan.polaris.services.MailService;
import com.talan.polaris.services.impl.MailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = Application.class)

public class MailIntegrationTests {
    @Autowired
    MailService mailService;

    @Test
    public void test () {
        mailService.sendQuizLinkMail("Wissem" ,
                "wissemwassar1@gmail.com",
                "www.facebook.com"
        );


    }
}
