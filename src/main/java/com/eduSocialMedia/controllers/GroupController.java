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
import com.eduSocialMedia.dtos.groups.GroupCreateDto;
import com.eduSocialMedia.dtos.groups.GroupFindDto;
import com.eduSocialMedia.dtos.groups.GroupResponseDto;
import com.eduSocialMedia.dtos.groups.GroupUpdateDto;
import com.eduSocialMedia.services.GroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/groups")
public class GroupController {
  @Autowired
  private GroupService groupService;

  @PostMapping
  public ResponseEntity<ResponseDto<GroupResponseDto>> create(@Valid @RequestBody GroupCreateDto groupCreateDto) {
    return this.groupService.create(groupCreateDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody GroupUpdateDto groupUpdateDto) {
    return this.groupService.update(id, groupUpdateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.groupService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupResponseDto>>> find(GroupFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.groupService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupResponseDto>> findById(@PathVariable Long id) {
    return this.groupService.findById(id);
  }
}
