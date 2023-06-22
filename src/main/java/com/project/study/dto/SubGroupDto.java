package com.project.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
}
