package com.project.study.service;

import com.project.study.dto.CommentsDto;
import com.project.study.dto.PostRequest;
import com.project.study.dto.PostResponse;
import com.project.study.dto.SubGroupDto;
import com.project.study.model.Comment;
import com.project.study.model.Post;
import com.project.study.model.SubGroup;
import com.project.study.model.User;
import com.project.study.repository.CommentRepository;
import com.project.study.repository.PostRepository;
import com.project.study.repository.SubGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;
    private final SubGroupRepository subGroupRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    public void save(PostRequest postRequest) throws Exception {
        SubGroup subGroup = subGroupRepository.findById(postRequest.getSubGroupId()).orElseThrow(Exception::new);
//        SubGroup subGroup = subGroupRepository.findByName(postRequest.getSubGroupName()).orElseThrow(Exception::new);
        Post post = new Post();
        post.setPostName(postRequest.getPostName());
        post.setDescription(postRequest.getDescription());
        post.setCreatedDate(java.time.Instant.now());
        post.setUser(userService.getCurrentUser());
        post.setImageName(postRequest.getImages());
        List<Comment> commentList = new ArrayList<>();

        if (!Objects.isNull(postRequest.getPostId())) {
            if (postRepository.findById(postRequest.getPostId()).isPresent()) {
                postRepository.findById(postRequest.getPostId()).get().getComment().stream().forEach(f -> {
                    commentList.add(f);
                });
            }
        }
        postRequest.getCommentList().stream().forEach(f -> {
            commentList.add(f);
        });
        post.setComment(commentList);
        post.setSubGroup(subGroup);

        postRepository.save(post);
    }

//    @Transactional(readOnly = true)
//    public List<PostResponse> getAllPosts() throws Exception {
////        List<Post> posts = postRepository.findAll();
//        List<SubGroup> subGroups = subGroupRepository.findAllByUsers(userService.getCurrentUser());
//        List<Post> posts = new ArrayList<>();
//        for(SubGroup subGroup: subGroups){
//            postRepository.findAllBySubGroup(subGroup).forEach(f->{
//                posts.add(f);
//            });
//        }
////        List<Post> posts = postRepository.findAllByUser_Id(userService.getCurrentUser().getId());
//        List<PostResponse> postResponses = new ArrayList<>(posts.size());
//        for (Post post : posts) {
//            PostResponse postResponse = new PostResponse();
//            postResponse.setId(post.getPostId());
//            postResponse.setPostName(post.getPostName());
//            postResponse.setDescription(post.getDescription());
//            postResponse.setUserName(post.getUser().getFirstname());
//            postResponse.setCreatedDate(post.getCreatedDate());
//            postResponse.setSubGroupName(post.getSubGroup());
//            postResponse.setImages(post.getImageName());
//            List<Comment> commentList = commentRepository.findAllByPost_PostId(post.getPostId());
//            List<CommentsDto> commentsDtos = new ArrayList<>(commentList.size());
//            for(Comment comment : commentList){
//                CommentsDto commentsDto = new CommentsDto();
//                commentsDto.setId(comment.getCommentId());
//                commentsDto.setText(comment.getText());
//                commentsDto.setCreatedDate(comment.getCreatedDate());
//                commentsDto.setUserName(comment.getUser().getUsername());
//                commentsDtos.add(commentsDto);
//            }
//            postResponse.setComment(commentsDtos);
//            postResponses.add(postResponse);
//        }
//        return postResponses;
//    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        List<SubGroup> subGroups = subGroupRepository.findAllByUsers(userService.getCurrentUser());
        List<PostResponse> postResponses = new ArrayList<>();
        for (SubGroup subGroup : subGroups) {
            Page<Post> postsPage = postRepository.findAllBySubGroup(subGroup, pageable);
           List<Post> posts = postsPage.getContent();
           for(Post post : posts){
               PostResponse postResponse = new PostResponse();
               postResponse.setId(post.getPostId());
            postResponse.setPostName(post.getPostName());
            postResponse.setDescription(post.getDescription());
            postResponse.setUserName(post.getUser().getFirstname());
            postResponse.setCreatedDate(post.getCreatedDate());
            postResponse.setSubGroupName(post.getSubGroup());
            postResponse.setImages(post.getImageName());
            postResponse.setPageSize(size);
            postResponse.setPageNumber(page);
            List<Comment> commentList = commentRepository.findAllByPost_PostId(post.getPostId());
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
               postResponses.add(postResponse);
           }
        }
        return new PageImpl<>(postResponses, pageable, postResponses.size());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllUserPosts() throws Exception {
        User user = userService.getCurrentUser();
        List<Post> posts = postRepository.findAllByUser(user);
        List<PostResponse> postResponses = new ArrayList<>(posts.size());
        for (Post post : posts) {
            PostResponse postResponse = new PostResponse();
            postResponse.setId(post.getPostId());
            postResponse.setPostName(post.getPostName());
            postResponse.setDescription(post.getDescription());
            postResponse.setUserName(post.getUser().getFirstname());
            postResponse.setCreatedDate(post.getCreatedDate());
            postResponse.setSubGroupName(post.getSubGroup());
//            Optional<SubGroup> subGroup = subGroupRepository.findById(post.getSubGroup().getSubGroupId());
            postResponse.setImages(post.getImageName());
            List<Comment> commentList = commentRepository.findAllByPost_PostId(post.getPostId());
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
            postResponses.add(postResponse);
        }
        return postResponses;
    }


    public void updatePost(Long id, PostRequest postRequest) throws Exception{
        Post post = postRepository.findById(id).orElseThrow(Exception::new);
//        post.setPostId(postRequest.getPostId());
        post.setPostName(postRequest.getPostName());
        post.setDescription(postRequest.getDescription());
        post.setComment(postRequest.getCommentList());
        post.setImageName(postRequest.getImages());
        SubGroup subGroup = subGroupRepository.findById(postRequest.getSubGroupId()).orElseThrow(Exception::new);
        post.setSubGroup(subGroup);
        postRepository.save(post);
    }

    public void deletePost(Long id) throws Exception {
        Post post = postRepository.findById(id).orElseThrow(Exception::new);
        commentRepository.deleteAll(commentRepository.findAllByPost_PostId(post.getPostId()));
        postRepository.delete(post);
    }
}
