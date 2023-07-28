package com.project.study.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.study.model.Comment;
import com.project.study.model.SubGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String description;
    private String userName;
    private SubGroup subGroupName;
    private Integer voteCount;
    private Integer commentCount;
    private Instant CreatedDate;
    private boolean upVote;
    private boolean downVote;
    private List<CommentsDto> comment = new ArrayList<>();
    private List images;
    private Integer pageNumber;
    private Integer pageSize;
}
