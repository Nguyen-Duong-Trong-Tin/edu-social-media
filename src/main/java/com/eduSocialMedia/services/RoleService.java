package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.roles.RoleAddPermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleCreateDto;
import com.eduSocialMedia.dtos.roles.RoleFindDto;
import com.eduSocialMedia.dtos.roles.RoleRemovePermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleReplacePermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleResponseDto;
import com.eduSocialMedia.dtos.roles.RoleUpdateDto;

public interface RoleService {
  ResponseEntity<ResponseDto<RoleResponseDto>> create(RoleCreateDto roleCreateDto);

  ResponseEntity<ResponseDto<RoleResponseDto>> addPermissions(Long id, RoleAddPermissionsDto roleAddPermissionsDto);

  ResponseEntity<ResponseDto<RoleResponseDto>> update(Long id, RoleUpdateDto roleUpdateDto);

  ResponseEntity<ResponseDto<RoleResponseDto>> replacePermissions(Long id, RoleReplacePermissionsDto roleReplacePermissionsDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<RoleResponseDto>> removePermissions(Long id, RoleRemovePermissionsDto roleRemovePermissionsDto);

  ResponseEntity<ResponseDto<ResponseSpecification<RoleResponseDto>>> find(RoleFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<RoleResponseDto>> findById(Long id);
}
