package com.project.study.service;


import com.project.study.dto.UserDto;
import com.project.study.model.User;
import com.project.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        User user = userRepository.findById(userId).get();
        return user;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(user.getUsername()).orElseThrow();
    }
}
