package com.project.study.service;

import com.project.study.dto.VoteDto;
import com.project.study.model.Post;
import com.project.study.model.Vote;
import com.project.study.repository.PostRepository;
import com.project.study.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.study.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    @Autowired
    VoteRepository voteRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;
    @Transactional
    public void vote(VoteDto voteDto) {
        Optional<Post> post = postRepository.findById(voteDto.getPostId());
//        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, userService.getCurrentUser());
//        if (voteByPostAndUser.isPresent() &&
//                voteByPostAndUser.get().getVoteType()
//                        .equals(voteDto.getVoteType())) {
//            throw new SpringRedditException("You have already "
//                    + voteDto.getVoteType() + "'d for this post");
//        }
//        if (UPVOTE.equals(voteDto.getVoteType())) {
//            post.setVoteCount(post.getVoteCount() + 1);
//        } else {
//            post.setVoteCount(post.getVoteCount() - 1);
//        }
//        voteRepository.save(mapToVote(voteDto, post));
//        postRepository.save(post);
    }
}
