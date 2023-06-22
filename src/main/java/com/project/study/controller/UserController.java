package com.project.study.controller;
import com.project.study.model.User;
import com.project.study.repository.UserRepository;
import com.project.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    private final UserRepository userRepository;

//    @GetMapping
//    public ResponseEntity getAllUsers(){
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping()
    public ResponseEntity getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUserDto());
    }
}
