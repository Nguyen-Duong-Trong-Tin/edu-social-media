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
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionCreateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionFindDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionResponseDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionUpdateDto;
import com.eduSocialMedia.services.GroupTaskSubmissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/group-task-submissions")
public class GroupTaskSubmissionController {
  @Autowired
  private GroupTaskSubmissionService groupTaskSubmissionService;

  @PostMapping
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> create(@Valid @RequestBody GroupTaskSubmissionCreateDto createDto) {
    return this.groupTaskSubmissionService.create(createDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody GroupTaskSubmissionUpdateDto updateDto) {
    return this.groupTaskSubmissionService.update(id, updateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.groupTaskSubmissionService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskSubmissionResponseDto>>> find(GroupTaskSubmissionFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.groupTaskSubmissionService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> findById(@PathVariable Long id) {
    return this.groupTaskSubmissionService.findById(id);
  }
}
