package com.talan.polaris.controllers;


import com.talan.polaris.services.MailSending;
import com.talan.polaris.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    MailSending mailSending;




    @PostMapping("/msg")
    public   void   send () throws MessagingException {

        mailSending.send("rihem.garrouche@etudiant-fst.utm.tn","aaaaaaaa","helooooo");


    }





}
