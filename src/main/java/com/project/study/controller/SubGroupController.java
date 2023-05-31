package com.project.study.controller;

import com.project.study.dto.SubGroupDto;
import com.project.study.service.SubGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/subGroup")
@AllArgsConstructor
@Slf4j
public class SubGroupController {
    private final SubGroupService subGroupService;

    @PostMapping
    public ResponseEntity<SubGroupDto> createSubGroup(@RequestBody SubGroupDto subGroupDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subGroupService.save(subGroupDto));
    }

    @GetMapping
    public ResponseEntity<List<SubGroupDto>> getAllSubGroups(){
        return ResponseEntity.status(HttpStatus.OK).body(subGroupService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubGroupDto> getSubgroup(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subGroupService.subGroupDto(id));
    }
}
