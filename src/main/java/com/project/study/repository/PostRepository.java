package com.project.study.repository;

import com.project.study.model.Post;
import com.project.study.model.SubGroup;
import com.project.study.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllBySubGroup(SubGroup subgroup, Pageable pageable);
//    List<Post> findAllBySubGroup(SubGroup subgroup);
//   Page<Post> findAllBySubGroupAndPage(SubGroup subgroup, Pageable pageable);


    List<Post> findAllByUser(User user);

    List<Post> findAllByUser_Id(Integer userId);
}
