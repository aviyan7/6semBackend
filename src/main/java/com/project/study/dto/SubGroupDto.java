package com.project.study.dto;

import com.project.study.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubGroupDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private List images;
    private Set<User> users;
}
