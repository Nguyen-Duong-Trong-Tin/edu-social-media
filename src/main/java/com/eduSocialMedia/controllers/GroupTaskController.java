package com.eduSocialMedia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskCreateDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskFindDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskResponseDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskUpdateDto;
import com.eduSocialMedia.services.GroupTaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/group-tasks")
public class GroupTaskController {
  @Autowired
  private GroupTaskService groupTaskService;

  @PostMapping
  public ResponseEntity<ResponseDto<GroupTaskResponseDto>> create(@Valid @RequestBody GroupTaskCreateDto createDto) {
    return this.groupTaskService.create(createDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupTaskResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody GroupTaskUpdateDto updateDto) {
    return this.groupTaskService.update(id, updateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.groupTaskService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskResponseDto>>> find(GroupTaskFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.groupTaskService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupTaskResponseDto>> findById(@PathVariable Long id) {
    return this.groupTaskService.findById(id);
  }
}
