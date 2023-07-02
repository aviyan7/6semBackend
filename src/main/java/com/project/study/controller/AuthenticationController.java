package com.project.study.controller;

import com.project.study.auth.AuthenticationRequest;
import com.project.study.auth.AuthenticationResponse;
import com.project.study.auth.RegisterRequest;
import com.project.study.service.AuthenticationService;
import com.project.study.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<AuthenticationResponse> register(
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

//    @PutMapping()
//    public ResponseEntity<ResponseEntity> verifyOtp(Integer Otp){
////        emailSenderService.verifyOtp(Otp);
//        return ResponseEntity.ok(service.verifyOtp(Otp));
//    }
}
