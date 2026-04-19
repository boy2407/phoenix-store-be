package com.example.phoenixstorebe.service;

public interface MailService {

    void sendVerificationMail(String to, String verifyLink, String username);

    void sendHtmlMail(String to, String subject, String htmlContent);
}
