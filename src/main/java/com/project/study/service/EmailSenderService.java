package com.project.study.service;

import com.project.study.auth.AuthenticationResponse;
import com.project.study.auth.RegisterRequest;
import com.project.study.config.JwtService;
import com.project.study.model.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtService jwtService;
//    @Autowired
//    private CacheManager cacheManager;

    private final String toEmail = "milanparajuli2058@gmail.com";
    private String randomNumber = String.valueOf(ThreadLocalRandom.current().nextInt(1000000));

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

    public AuthenticationResponse registerUser(RegisterRequest request){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("nayivadahal@gmail.com");
        simpleMailMessage.setTo(request.getEmail());
        simpleMailMessage.setSubject("Registration For StudySync");
        simpleMailMessage.setText("Welcome"+' '+request.getFirstName()+' '+request.getLastName()+"\n"
        +"Your One Time Password is "+randomNumber);
        this.javaMailSender.send(simpleMailMessage);
        var otpToken = jwtService.generateOtpToken(Integer.valueOf(randomNumber));
        return AuthenticationResponse.builder()
                .token(otpToken)
                .build();
//        return randomNumber;
    }

    public void forgotPassword(String email){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("tuckingtrue@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Password Reset For StudySync");
        simpleMailMessage.setText("Your OTP for reseting password is"+randomNumber);
        this.javaMailSender.send(simpleMailMessage);
//        Cache cache = cacheManager.getCache("randomNumber");
//        cache.put(email, randomNumber);
    }

//    public void verifyOtp(Integer otp){
//        if(otp == randomNumber)
//    }

}
