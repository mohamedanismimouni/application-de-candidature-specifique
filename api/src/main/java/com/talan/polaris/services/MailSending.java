package com.talan.polaris.services;

import javax.mail.MessagingException;

public interface MailSending {
    
    void send(String to, String subject, String body) throws MessagingException;

    void sendQuizToCandidate (String to, String subject, String body) throws MessagingException;

    
}
