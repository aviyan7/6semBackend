package com.project.study.controller;

import com.project.study.dto.PostRequest;
import com.project.study.dto.PostResponse;
import com.project.study.model.Post;
import com.project.study.repository.PostRepository;
import com.project.study.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) throws Exception {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<List<PostResponse>> getAllPosts() throws Exception {
//        return status(HttpStatus.OK).body(postService.getAllPosts());
//    }

    @GetMapping("/user")
    public ResponseEntity<List<PostResponse>> getAllUserPosts() throws Exception {
        return status(HttpStatus.OK).body(postService.getAllUserPosts());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) throws Exception {
        postService.updatePost(id, postRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) throws Exception {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Post>> getPageablePost(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "2")int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAll(pageable);
        System.out.println("Posts"+posts);
        return status(HttpStatus.OK).body(posts);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "2")int size) throws Exception {
        return status(HttpStatus.OK).body(postService.getAllPosts(page, size));
    }

}
