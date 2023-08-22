package com.project.study.service;

import com.project.study.dto.PostRequest;
import com.project.study.model.SubGroup;
import com.project.study.model.User;
import com.project.study.repository.NotificationRepository;
import com.project.study.repository.SubGroupRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    private SubGroupRepository subGroupRepository;
    @Autowired
    EmailSenderService emailSenderService;

    public void sendNotification(PostRequest postRequest) throws Exception{
        SubGroup subGroup = subGroupRepository.findById(postRequest.getSubGroupId()).orElseThrow(Exception::new);
        subGroup.getUsers().forEach(f->{
            try {
                emailSenderService.sendNotificationByEmail(f.getEmail(), subGroup.getName(), postRequest);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
