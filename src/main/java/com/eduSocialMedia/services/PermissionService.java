package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.permissions.PermissionCreateDto;
import com.eduSocialMedia.dtos.permissions.PermissionFindDto;
import com.eduSocialMedia.dtos.permissions.PermissionResponseDto;
import com.eduSocialMedia.dtos.permissions.PermissionUpdateDto;

public interface PermissionService {
  ResponseEntity<ResponseDto<PermissionResponseDto>> create(PermissionCreateDto permissionCreateDto);

  ResponseEntity<ResponseDto<PermissionResponseDto>> update(Long id, PermissionUpdateDto permissionUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<PermissionResponseDto>>> find(PermissionFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<PermissionResponseDto>> findById(Long id);
}