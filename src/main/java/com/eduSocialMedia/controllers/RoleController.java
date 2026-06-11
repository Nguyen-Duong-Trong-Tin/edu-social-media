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
import com.eduSocialMedia.dtos.roles.RoleAddPermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleCreateDto;
import com.eduSocialMedia.dtos.roles.RoleFindDto;
import com.eduSocialMedia.dtos.roles.RoleRemovePermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleReplacePermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleResponseDto;
import com.eduSocialMedia.dtos.roles.RoleUpdateDto;
import com.eduSocialMedia.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {
  @Autowired
  private RoleService roleService;

  @PostMapping
  // @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<RoleResponseDto>> create(@Valid @RequestBody RoleCreateDto roleCreateDto) {
    return this.roleService.create(roleCreateDto);
  }

  @PostMapping("/{id}/permissions")
  public ResponseEntity<ResponseDto<RoleResponseDto>> addPermissions(@PathVariable Long id, @Valid @RequestBody RoleAddPermissionsDto roleAddPermissionsDto) {
    return this.roleService.addPermissions(id, roleAddPermissionsDto);
  }

  @PatchMapping("/{id}")
  // @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<RoleResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody RoleUpdateDto roleUpdateDto) {
    return this.roleService.update(id, roleUpdateDto);
  }

  @PatchMapping("/{id}/permissions")
  public ResponseEntity<ResponseDto<RoleResponseDto>> replacePermissions(@PathVariable Long id, @Valid @RequestBody RoleReplacePermissionsDto roleReplacePermissionsDto) {
    return this.roleService.replacePermissions(id, roleReplacePermissionsDto);
  }

  @DeleteMapping("/{id}")
  // @Auth({ "Admin" })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.roleService.delete(id);
  }

  @DeleteMapping("/{id}/permissions")
  public ResponseEntity<ResponseDto<RoleResponseDto>> removePermissions(@PathVariable Long id, @Valid @RequestBody RoleRemovePermissionsDto roleRemovePermissionsDto) {
    return this.roleService.removePermissions(id, roleRemovePermissionsDto);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<RoleResponseDto>>> find(RoleFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.roleService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<RoleResponseDto>> findById(@PathVariable Long id) {
    return this.roleService.findById(id);
  }
}
