package com.project.study.controller;

import com.project.study.repository.NotificationRepository;
import com.project.study.service.NotificationService;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationRepository notificationRepository;

//    @PostMapping
//    public ResponseEntity<Void> sendNotification(@RequestBody Po)
}
