package com.project.study.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Instant;

import static jakarta.persistence.GenerationType.IDENTITY;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;

    @NonNull
    private String postName;

    @Nullable
    @Lob
    private String description;

    private Instant createdDate;
    @ManyToOne
    @JoinColumn(name="id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "subGroupId", referencedColumnName = "subGroupId")
    private SubGroup subGroup;

}
