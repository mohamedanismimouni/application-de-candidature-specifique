//package com.talan.polaris.services.impl;
//
//import com.talan.polaris.dto.CandidateDTO;
//import com.talan.polaris.entities.CandidateEntity;
//import com.talan.polaris.services.MailSending;
//import com.talan.polaris.services.TestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//
//@Service
//public class MailSendingImpl implements MailSending {
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//
//
//
//    @Override
//    public void send (String to, String subject, String body) throws MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper;
//
//        helper = new MimeMessageHelper(message, true); // true indicates
//        helper.setFrom("anismimouni8@gmail.com");
//        helper.setSubject(subject);
//        helper.setTo(to);
//        helper.setText(body, true);
//        javaMailSender.send(message);
//
//        System.out.println("mail sent successfully");
//    }
//
//    @Override
//    public void sendQuizToCandidate(String to, String subject, String body) throws MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper;
//        helper = new MimeMessageHelper(message, true); // true indicates
//    }
//}
