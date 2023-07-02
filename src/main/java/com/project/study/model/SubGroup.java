package com.project.study.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SubGroup {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long subGroupId;
    private String name;
    private String description;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;
    @ManyToMany(fetch = LAZY)
    private Set<User> users;
//    private List<User> users;

    @ElementCollection
    private List<String> imageName;
}
