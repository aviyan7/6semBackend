package com.project.study.repository;

import com.project.study.model.SubGroup;
import com.project.study.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SubGroupRepository extends JpaRepository<SubGroup, Long> {

    Optional<SubGroup> findByName(String subGroupName);

    @Query("SELECT g FROM SubGroup g where g.users <> :user")
    List<SubGroup> findAllGroupsNotContainingUser(User user);

    List<SubGroup> findAllByUsers(User user);
}
