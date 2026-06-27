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
import com.eduSocialMedia.dtos.groupTopics.GroupTopicCreateDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicFindDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicResponseDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicUpdateDto;
import com.eduSocialMedia.services.GroupTopicService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/group-topics")
public class GroupTopicController {
  @Autowired
  private GroupTopicService groupTopicService;

  @PostMapping
  public ResponseEntity<ResponseDto<GroupTopicResponseDto>> create(@Valid @RequestBody GroupTopicCreateDto groupTopicCreateDto) {
    return this.groupTopicService.create(groupTopicCreateDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupTopicResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody GroupTopicUpdateDto groupTopicUpdateDto) {
    return this.groupTopicService.update(id, groupTopicUpdateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.groupTopicService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTopicResponseDto>>> find(GroupTopicFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.groupTopicService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupTopicResponseDto>> findById(@PathVariable Long id) {
    return this.groupTopicService.findById(id);
  }
}
