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
import com.eduSocialMedia.dtos.permissions.PermissionCreateDto;
import com.eduSocialMedia.dtos.permissions.PermissionFindDto;
import com.eduSocialMedia.dtos.permissions.PermissionResponseDto;
import com.eduSocialMedia.dtos.permissions.PermissionUpdateDto;
import com.eduSocialMedia.services.PermissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/permissions")
public class PermissionController {
  @Autowired
  private PermissionService permissionService;

  @PostMapping
  public ResponseEntity<ResponseDto<PermissionResponseDto>> create(@Valid @RequestBody PermissionCreateDto permissionCreateDto) {
    return this.permissionService.create(permissionCreateDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<PermissionResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody PermissionUpdateDto permissionUpdateDto) {
    return this.permissionService.update(id, permissionUpdateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.permissionService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<PermissionResponseDto>>> find(PermissionFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.permissionService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<PermissionResponseDto>> findById(@PathVariable Long id) {
    return this.permissionService.findById(id);
  }
}