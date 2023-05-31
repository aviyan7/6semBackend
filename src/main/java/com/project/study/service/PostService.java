package com.project.study.service;

import com.project.study.dto.PostRequest;
import com.project.study.dto.PostResponse;
import com.project.study.model.Post;
import com.project.study.model.SubGroup;
import com.project.study.repository.PostRepository;
import com.project.study.repository.SubGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubGroupRepository subGroupRepository;
    private final UserService userService;

    public void save(PostRequest postRequest) {
        SubGroup subGroup = subGroupRepository.findByName(postRequest.getSubGroupName()).orElseThrow();
        Post post = new Post();
        post.setPostName(postRequest.getPostName());
        post.setDescription(postRequest.getDescription());
        post.setCreatedDate(java.time.Instant.now());
        post.setUser(userService.getCurrentUser());
        post.setSubGroup(subGroup);

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
      List<Post> posts = postRepository.findAll();
      List<PostResponse> postResponses = new ArrayList<>(posts.size());
      for (Post post: posts){
       PostResponse postResponse = new PostResponse();
       postResponse.setId(post.getPostId());
       postResponse.setPostName(post.getPostName());
       postResponse.setDescription(post.getDescription());
       postResponse.setUserName(post.getUser().getUsername());
       postResponses.add(postResponse);
               }
      return postResponses;
    }
}
