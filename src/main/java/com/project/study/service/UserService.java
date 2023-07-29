package com.project.study.service;


import com.project.study.dto.ResetPassword;
import com.project.study.dto.UserDto;
import com.project.study.model.User;
import com.project.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        return user;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(user.getUsername()).orElseThrow();
    }


    @Transactional(readOnly = true)
    public UserDto getCurrentUserDto() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User foundUser = userRepository.findByEmail(user.getUsername()).orElseThrow();
        UserDto userDto = new UserDto();
        userDto.setId(foundUser.getId());
        userDto.setFirstName(foundUser.getFirstname());
        userDto.setLastName(foundUser.getLastname());
        userDto.setEmail(foundUser.getEmail());
        userDto.setAddress(foundUser.getAddress());

        return userDto;
    }

    public void updateUser(Long id, UserDto userDto) throws Exception {
        User user = userRepository.findById(id).orElseThrow(Exception::new);
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        if(user.getEmail() != userDto.getEmail()){
            userRepository.findByEmail(userDto.getEmail()).orElseThrow();
            user.setEmail(userDto.getEmail());
        }
        user.setAddress(userDto.getAddress());
        userRepository.save(user);
    }

    public void deleteUser(Long id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(Exception::new);
        userRepository.delete(user);
    }

    public void resetPassword(ResetPassword resetPassword) throws Exception {
        User user = userRepository.findByEmail(resetPassword.getEmail()).orElseThrow(Exception::new);
        user.getPassword();
        resetPassword.getCurrentPassword();
        passwordEncoder.encode(resetPassword.getCurrentPassword());
    }
}
