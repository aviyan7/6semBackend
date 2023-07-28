package com.project.study.controller;
import com.project.study.dto.SubGroupDto;
import com.project.study.dto.UserDto;
import com.project.study.model.User;
import com.project.study.repository.UserRepository;
import com.project.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping()
    public ResponseEntity getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUserDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws Exception {
        userService.updateUser(id, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Void> getPageableUser(@RequestBody String username, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "3")int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findUserByUsername(username, pageable);
        System.out.println("Users"+users);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
