package com.project.study.controller;

import com.project.study.dto.CommentsDto;
import com.project.study.dto.PostRequest;
import com.project.study.dto.PostResponse;
import com.project.study.model.Comment;
import com.project.study.model.Post;
import com.project.study.repository.CommentRepository;
import com.project.study.repository.PostRepository;
import com.project.study.service.NotificationService;
import com.project.study.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    NotificationService notificationService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) throws Exception {
        postService.save(postRequest);
        notificationService.sendNotification(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<List<PostResponse>> getAllPosts() throws Exception {
//        return status(HttpStatus.OK).body(postService.getAllPosts());
//    }

//    @GetMapping("/user")
//    public ResponseEntity<List<PostResponse>> getAllUserPosts() throws Exception {
//        return status(HttpStatus.OK).body(postService.getAllUserPosts());
//    }

    @GetMapping("/user")
    public ResponseEntity<Page<PostResponse>> getAllUserPosts(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "4") int size) throws Exception {
        return status(HttpStatus.OK).body(postService.getAllUserPosts(page, size));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) throws Exception {
        postService.updatePost(id, postRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/recommendation/{id}")
    public ResponseEntity<List<PostResponse>> getSimilarPosts(@PathVariable Long id) {
        Post targetPost = postRepository.findById(id).orElseThrow();

        if (targetPost == null) {
            return ResponseEntity.notFound().build();
        }

        // Get all posts from the database
        List<Post> allPostsOfUser = postRepository.findAll();

        // Calculate cosine similarity and get recommendations
       List<Long> recommendations = postService.findSimilarPosts(allPostsOfUser, targetPost);
       List<Post> p = new ArrayList<>();
       List<PostResponse> finalRecommendations = new ArrayList<>();
       recommendations.forEach(f->{
           Post post = postRepository.findById(id).orElseThrow();
           p.add(post);
       });
       p.forEach(f->{
           PostResponse postResponse = new PostResponse();
           postResponse.setId(f.getPostId());
           postResponse.setPostName(f.getPostName());
           postResponse.setDescription(f.getDescription());
           postResponse.setUserName(f.getUser().getFirstname());
           postResponse.setCreatedDate(f.getCreatedDate());
           postResponse.setSubGroupName(f.getSubGroup());
           postResponse.setImages(f.getImageName());
           List<Comment> commentList = commentRepository.findAllByPost_PostId(f.getPostId());
           List<CommentsDto> commentsDtos = new ArrayList<>(commentList.size());
           for(Comment comment : commentList){
               CommentsDto commentsDto = new CommentsDto();
               commentsDto.setId(comment.getCommentId());
               commentsDto.setText(comment.getText());
               commentsDto.setCreatedDate(comment.getCreatedDate());
               commentsDto.setUserName(comment.getUser().getUsername());
               commentsDtos.add(commentsDto);
           }
           postResponse.setComment(commentsDtos);
           finalRecommendations.add(postResponse);
       });

        return ResponseEntity.ok(finalRecommendations);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) throws Exception {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Post>> getPageablePost(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "4")int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAll(pageable);
        System.out.println("Posts"+posts);
        return status(HttpStatus.OK).body(posts);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "4")int size) throws Exception {
        return status(HttpStatus.OK).body(postService.getAllPosts(page, size));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PostResponse>> getTotalPosts(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "4")int size) throws Exception {
        return status(HttpStatus.OK).body(postService.getTotalPosts(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPost(@RequestParam String keyword) throws Exception {
        return status(HttpStatus.OK).body(postService.searchPost(keyword));
    }

}
