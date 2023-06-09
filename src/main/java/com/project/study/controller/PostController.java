package com.project.study.controller;

import com.project.study.dto.PostRequest;
import com.project.study.dto.PostResponse;
import com.project.study.model.Post;
import com.project.study.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) throws Exception {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() throws Exception {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<List<PostResponse>> updatePost(){
//        return status(HttpStatus.OK).body(postService.updatePost(id));
//    }

}
