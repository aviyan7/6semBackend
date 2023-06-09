package com.project.study.dto;

import com.project.study.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    private String subGroupName;
    private String postName;
    private String description;

    private List<Comment> commentList = new ArrayList<>();
}
