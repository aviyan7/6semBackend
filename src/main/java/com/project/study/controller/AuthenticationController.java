package com.project.study.controller;

import com.project.study.auth.AuthenticationRequest;
import com.project.study.auth.AuthenticationResponse;
import com.project.study.auth.RegisterRequest;
import com.project.study.model.OtpRequest;
import com.project.study.service.AuthenticationService;
import com.project.study.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailSenderService emailSenderService;
    private Integer otpNumber;
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ){
//        emailSenderService.registerUser(request);
       return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<HttpStatus> verifyOtp(@RequestBody OtpRequest otpRequest){
        return ResponseEntity.ok(emailSenderService.verifyOtp(otpRequest));
//        return ResponseEntity.ok(service.verifyOtp(Otp));
    }
}
