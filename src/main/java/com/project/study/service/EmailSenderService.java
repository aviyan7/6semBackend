package com.project.study.service;

import com.project.study.auth.RegisterRequest;
import com.project.study.config.JwtService;
import com.project.study.dto.PostRequest;
import com.project.study.model.EmailMessage;
import com.project.study.model.OtpRequest;
import com.project.study.model.User;
import com.project.study.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtService jwtService;
//    @Autowire
//    private CacheManager cacheManager;

    private final String toEmail = "tuckingtrue@gmail.com";
    private final String resetLink = "http://localhost:4200/#/auth/verify-password";
    private String viewPostLink = "http://localhost:4200/view-post/";
    private String randomNumber = String.valueOf(ThreadLocalRandom.current().nextInt(1000000));
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public void sendEmail(EmailMessage emailMessage) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailMessage.getEmail());
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject("Contact from website");
        simpleMailMessage.setText(
                "Name:" + emailMessage.getName() + "\nPhone Number:" + emailMessage.getPhoneNumber()
                        + "\nEmail:" + emailMessage.getEmail() + "\nMessage:" + emailMessage.getMessage());
        System.out.println(simpleMailMessage);
        this.javaMailSender.send(simpleMailMessage);
    }

    public String registerUser(RegisterRequest request){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("nayivadahal@gmail.com");
        simpleMailMessage.setTo(request.getEmail());
        simpleMailMessage.setSubject("Registration For StudySync");
        simpleMailMessage.setText("Welcome"+' '+request.getFirstName()+' '+request.getLastName()+"\n"
        +"Your One Time Password is "+randomNumber);
        System.out.println(simpleMailMessage);
        this.javaMailSender.send(simpleMailMessage);
//        var otpToken = jwtService.generateOtpToken(Integer.valueOf(randomNumber));
//        return AuthenticationResponse.builder()
//                .token(otpToken)
//                .build();
       return randomNumber;
    }

    public void forgotPassword(String email) throws MessagingException {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("tuckingtrue@gmail.com");
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setSubject("Password Reset For StudySync");
//        simpleMailMessage.setText("Your OTP for reseting password is"+randomNumber);
//        this.javaMailSender.send(simpleMailMessage);
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Password Reset For StudySync");
        String htmlContent = "<p>Please click the button below to reset your password:</p>"
                + "<p><a href=\"" + resetLink + "\">"
                + "<button style=\"background-color: #4CAF50; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer;\">Reset Password</button>"
                + "</a></p>";
        helper.setText(htmlContent, true);
        System.out.println(message);
        this.javaMailSender.send(message);
    }

    public HttpStatus verifyOtp(OtpRequest otpRequest){
        if(otpRequest.getOtp().equals(randomNumber)){
            Optional<User> user = userRepository.findByEmail(otpRequest.getEmail());
            if(user.isPresent()){
                user.get().setEnabled(true);
                userRepository.save(user.get());
            }
            return HttpStatus.CREATED;
        }
        return HttpStatus.EXPECTATION_FAILED;
    }

    public void sendNotificationByEmail(String to, String subGroupName, PostRequest postRequest) throws MessagingException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("New Post Added in group "+subGroupName+" Titled : " +postRequest.getPostName());
        String htmlContent = "<p>New Post has been added in the group you are a member of. Please click the button below to view the post:</p>"
                + "<p><a href=\"" + viewPostLink + postRequest.getPostName()+"\">"
                + "<button style=\"background-color: #4CAF50; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer;\">View Post</button>"
                + "</a></p>";
        helper.setText(htmlContent, true);
        System.out.println(message);
        javaMailSender.send(message);
    }

}
