package com.project.study.service;

import com.project.study.dto.CommentsDto;
import com.project.study.dto.PostRequest;
import com.project.study.dto.PostResponse;
import com.project.study.model.Comment;
import com.project.study.model.Post;
import com.project.study.model.SubGroup;
import com.project.study.model.User;
import com.project.study.repository.CommentRepository;
import com.project.study.repository.PostRepository;
import com.project.study.repository.SubGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public Page<PostResponse> getTotalPosts(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        List<SubGroup> subGroups = subGroupRepository.findAll();
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

//    @Transactional(readOnly = true)
//    public List<PostResponse> getAllUserPosts() throws Exception {
//        User user = userService.getCurrentUser();
//        List<Post> posts = postRepository.findAllByUser(user);
//        List<PostResponse> postResponses = new ArrayList<>(posts.size());
//        for (Post post : posts) {
//            PostResponse postResponse = new PostResponse();
//            postResponse.setId(post.getPostId());
//            postResponse.setPostName(post.getPostName());
//            postResponse.setDescription(post.getDescription());
//            postResponse.setUserName(post.getUser().getFirstname());
//            postResponse.setCreatedDate(post.getCreatedDate());
//            postResponse.setSubGroupName(post.getSubGroup());
////            Optional<SubGroup> subGroup = subGroupRepository.findById(post.getSubGroup().getSubGroupId());
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
    public Page<PostResponse> getAllUserPosts(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        User user = userService.getCurrentUser();
        List<Post> posts = postRepository.findAllByUser(user, pageable);
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
        return new PageImpl<>(postResponses, pageable, postResponses.size());
    }

    private static Map<String, Map<Long, Double>> calculateTFIDF(List<Post> posts) {
        Map<String, Map<Long, Double>> tfidfVectors = new HashMap<>();
        Map<String, Integer> documentFrequency = new HashMap<>();

        // Calculate term frequency (TF)
        for (Post post : posts) {
            String content = post.getPostName() + " " + post.getDescription();
            content = content.toLowerCase();

            String[] words = content.split("\\s+");
            Map<Long, Double> tfVector = new HashMap<>();

            for (String word : words) {
                tfVector.put(post.getPostId(), tfVector.getOrDefault(post.getPostId(), 0.0) + 1);
            }

            // Update document frequency
            for (Long word : new HashSet<>(tfVector.keySet())) {
                documentFrequency.put(String.valueOf(word), documentFrequency.getOrDefault(word, 0) + 1);
            }

            tfidfVectors.put(content, tfVector);
        }

        // Calculate inverse document frequency (IDF) and update TF-IDF vectors
//        for (Map.Entry<String, Map<Long, Double>> entry : tfidfVectors.entrySet()) {
//            String content = entry.getKey();
//            Map<Long, Double> tfVector = entry.getValue();
//
//            for (Long word : tfVector.keySet()) {
//                double tf = tfVector.get(word);
//                double idf = Math.log((double) posts.size() / documentFrequency.get(word));
//                tfVector.put(word, tf * idf);
//            }
//        }

        for (Map.Entry<String, Map<Long, Double>> entry : tfidfVectors.entrySet()) {
            String content = entry.getKey();
            Map<Long, Double> tfVector = entry.getValue();

            for (Long word : tfVector.keySet()) {
                double tf = tfVector.get(word);

                // Check if the word is present in the documentFrequency map
                Integer documentFreq = documentFrequency.get(word.toString());
                if (documentFreq != null && documentFreq != 0) {
                    double idf = Math.log((double) posts.size() / documentFreq);
                    tfVector.put(word, tf * idf);
                } else {
                    // Handle the case where document frequency is not available
                    // You might want to set a default value or handle it based on your requirements
                }
            }
        }

        return tfidfVectors;
    }


    private static double calculateCosineSimilarity(Map<Long, Double> vector1, Map<Long, Double> vector2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        if(vector1 !=null && vector2 !=null){
            for (long postId : vector1.keySet()) {
                dotProduct += vector1.get(postId) * vector2.getOrDefault(postId, 0.1);
                norm1 += Math.pow(vector1.get(postId), 2);
            }
            for (double value : vector2.values()) {
                norm2 += Math.pow(value, 2);
            }
        }

        if (norm1 == 0.0 || norm2 == 0.0) {
            return 0.0; // To handle division by zero
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }


    public static List<Long> findSimilarPosts(List<Post> posts, Post targetPost) {
        Map<String, Map<Long, Double>> tfidfVectors = calculateTFIDF(posts);
        Map<Long, Double> targetVector = tfidfVectors.get((targetPost.getPostName() + " " + targetPost.getDescription()).toLowerCase());

        List<Long> similarPosts = new ArrayList<>();

        for (Post post : posts) {
            if (!post.getPostId().equals(targetPost.getPostId())) {
                double similarity = calculateCosineSimilarity(targetVector,
                        tfidfVectors.get((post.getPostName() + " " + post.getDescription()).toLowerCase()));
                if (similarity > 0.0) { // Adjust the threshold as needed
                    similarPosts.add(post.getPostId());
                }
            }
        }

        return similarPosts;
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

    public List<Post> searchPost(String Keyword) throws Exception {
//        User user = userService.getCurrentUser();
//        List<SubGroup> subGroups = subGroupRepository.findAllByUsers(user);
//        for(SubGroup subGroup: subGroups){
//           List<Post> posts = postRepository.findAll();
//        }
        List<Post> posts = postRepository.searchPosts(Keyword);
        return posts;
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
            PostResponse postResponse = new PostResponse();
            postResponse.setId(post.getPostId());
            postResponse.setPostName(post.getPostName());
            postResponse.setDescription(post.getDescription());
            postResponse.setUserName(post.getUser().getFirstname());
            postResponse.setCreatedDate(post.getCreatedDate());
            postResponse.setSubGroupName(post.getSubGroup());
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
        return postResponse;
    }
}
