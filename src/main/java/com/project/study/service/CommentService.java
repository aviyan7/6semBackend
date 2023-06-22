package com.project.study.service;

import com.project.study.dto.CommentsDto;
import com.project.study.model.Comment;
import com.project.study.model.Post;
import com.project.study.repository.CommentRepository;
import com.project.study.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    public void save(CommentsDto commentsDto) throws Exception {
        Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(Exception::new);
        Comment comment = new Comment();

        comment.setUser(userService.getCurrentUser());
        comment.setPost(post);
        comment.setText(commentsDto.getText());
        comment.setCreatedDate(java.time.Instant.now());
        commentRepository.save(comment);
    }
}
