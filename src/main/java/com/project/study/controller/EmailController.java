package com.project.study.controller;

import com.project.study.model.EmailMessage;
import com.project.study.service.EmailSenderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/email")
public class EmailController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/email-send")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> sendEmail(@RequestBody EmailMessage emailMessage) {
        this.emailSenderService.sendEmail(emailMessage);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/forgot-password")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> forgotPassword(@RequestBody String email) throws MessagingException {
        this.emailSenderService.forgotPassword(email);
//        return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.ok("Success");
    }
}
