package com.project.study.service;

import com.project.study.dto.SubGroupDto;
import com.project.study.model.SubGroup;
import com.project.study.repository.SubGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubGroupService {
    private final SubGroupRepository subGroupRepository;

    @Transactional
    public SubGroupDto save(SubGroupDto subGroupDto){
        SubGroup subGroup = new SubGroup();
        subGroup.setName(subGroupDto.getName());
        subGroup.setDescription(subGroupDto.getDescription());
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
}
