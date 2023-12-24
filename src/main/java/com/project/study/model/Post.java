package com.project.study.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Column(length = 255)
    private String description;

    private Instant createdDate;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "subGroupId", referencedColumnName = "subGroupId")
    private SubGroup subGroup;

    @OneToMany
    @Nullable
    private List<Comment> comment = new ArrayList<>();

    @ElementCollection
    private List<String> imageName;

}
