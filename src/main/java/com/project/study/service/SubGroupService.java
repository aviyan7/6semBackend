package com.project.study.service;

import com.project.study.dto.SubGroupDto;
import com.project.study.model.SubGroup;
import com.project.study.model.User;
import com.project.study.repository.SubGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class SubGroupService {
    @Autowired
    SubGroupRepository subGroupRepository;

    @Autowired
    UserService userService;

    @Transactional
    public SubGroupDto save(SubGroupDto subGroupDto){
        SubGroup subGroup = new SubGroup();
        subGroup.setName(subGroupDto.getName());
        subGroup.setDescription(subGroupDto.getDescription());
        subGroup.setImageName(subGroupDto.getImages());
        subGroup.setCreatedDate(java.time.Instant.now());
//        subGroup.setUser(userService.getCurrentUser());
        Set<User> users = new HashSet<>();
        users.add(userService.getCurrentUser());
        subGroup.setUsers(users);
        SubGroup savedSubGroup = subGroupRepository.save(subGroup);
        subGroupDto.setId(savedSubGroup.getSubGroupId());
        return subGroupDto;
    }

    @Transactional (readOnly = true)
    public List<SubGroupDto> getAll() {
        List<SubGroup> subgroups = subGroupRepository.findAll();
        List<SubGroupDto> subGroupDtos = new ArrayList<>(subgroups.size());
        for (SubGroup subGroup : subgroups) {
            SubGroupDto subGroupDto = new SubGroupDto();
            subGroupDto.setId(subGroup.getSubGroupId());
            subGroupDto.setName(subGroup.getName());
            subGroupDto.setDescription(subGroup.getDescription());
            subGroupDto.setNumberOfPosts(subGroup.getPosts().size());
            subGroupDtos.add(subGroupDto);
        }
        return subGroupDtos;
    }

    public SubGroupDto subGroupDto(Long id) {
        SubGroup subGroup = subGroupRepository.findById(id).orElseThrow();
        SubGroupDto subGroupDto = new SubGroupDto();
        subGroupDto.setId(subGroup.getSubGroupId());
        subGroupDto.setName(subGroup.getName());
        subGroupDto.setDescription(subGroup.getDescription());
        return subGroupDto;
    }

    public SubGroupDto updateSubGroup(Long id) {
        SubGroup subGroup = subGroupRepository.findById(id).orElseThrow();
        Set<User> users = subGroup.getUsers();
        users.add(userService.getCurrentUser());
        subGroup.setUsers(users);
        subGroupRepository.save(subGroup);
//        subGroup.setUsers(subGroup.getUsers().add(userService.getCurrentUser()));
        SubGroupDto subGroupDto = new SubGroupDto();
        subGroupDto.setId(subGroup.getSubGroupId());
        subGroupDto.setName(subGroup.getName());
        subGroupDto.setDescription(subGroup.getDescription());
        subGroupDto.setUsers(subGroup.getUsers());
        return subGroupDto;
//        return ResponseEntity;
    }

    @Transactional (readOnly = true)
    public List<SubGroupDto> getAllSubGroupWithoutUser() {
        List<SubGroup> subgroups = subGroupRepository.findAllGroupsNotContainingUser(userService.getCurrentUser());
        List<SubGroupDto> subGroupDtos = new ArrayList<>(subgroups.size());
        for (SubGroup subGroup : subgroups) {
            SubGroupDto subGroupDto = new SubGroupDto();
            subGroupDto.setId(subGroup.getSubGroupId());
            subGroupDto.setName(subGroup.getName());
            subGroupDto.setDescription(subGroup.getDescription());
            subGroupDto.setNumberOfPosts(subGroup.getPosts().size());
            subGroupDto.setUsers(subGroup.getUsers());
            subGroupDtos.add(subGroupDto);
        }
        return subGroupDtos;
    }
}
