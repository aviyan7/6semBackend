package com.project.study.service;

import com.project.study.model.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    private final String toEmail = "jaslaiemailpathauneteskoemail";

    public void sendEmail(EmailMessage emailMessage) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailMessage.getEmail());
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject("Contact from website");
        simpleMailMessage.setText(
                "Name:" + emailMessage.getName() + "\nPhone Number:" + emailMessage.getPhoneNumber()
                        + "\nEmail:" + emailMessage.getEmail() + "\nMessage:" + emailMessage.getMessage());
        this.javaMailSender.send(simpleMailMessage);
    }
}
