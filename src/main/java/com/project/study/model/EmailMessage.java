package com.project.study.model;

import lombok.Data;

@Data
public class EmailMessage {
    private String cc;

    private String subject;

    private String name;

    private String phoneNumber;

    private String email;

    private String message;
}
