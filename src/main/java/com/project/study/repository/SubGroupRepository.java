package com.project.study.repository;

import com.project.study.model.SubGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SubGroupRepository extends JpaRepository<SubGroup, Long> {

    Optional<SubGroup> findByName(String subGroupName);
}
