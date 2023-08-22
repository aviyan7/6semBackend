package com.project.study.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long notificationId;

    private String title;
    private String description;

}
