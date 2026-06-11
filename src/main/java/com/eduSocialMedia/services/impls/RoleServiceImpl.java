package com.eduSocialMedia.services.impls;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.RoleConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.roles.RoleAddPermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleCreateDto;
import com.eduSocialMedia.dtos.roles.RoleFindDto;
import com.eduSocialMedia.dtos.roles.RoleRemovePermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleReplacePermissionsDto;
import com.eduSocialMedia.dtos.roles.RoleResponseDto;
import com.eduSocialMedia.dtos.roles.RoleUpdateDto;
import com.eduSocialMedia.repositories.PermissionRepository;
import com.eduSocialMedia.repositories.RoleRepository;
import com.eduSocialMedia.repositories.entities.PermissionEntity;
import com.eduSocialMedia.repositories.entities.RoleEntity;
import com.eduSocialMedia.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  private RoleConverter roleConverter;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PermissionRepository permissionRepository;

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> create(RoleCreateDto roleCreateDto) {
    RoleEntity roleEntity = this.roleConverter.toRoleEntity(roleCreateDto);

    roleEntity = this.roleRepository.save(roleEntity);

    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);

    return ResponseDto.success(roleResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> addPermissions(Long id,
      RoleAddPermissionsDto roleAddPermissionsDto) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);
    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    List<PermissionEntity> permissionEntities = this.permissionRepository.findAllById(roleAddPermissionsDto.getPermissionIds());
    if (permissionEntities.size() != roleAddPermissionsDto.getPermissionIds().size()) {
      return ResponseDto.notFound("Some permissions not found");
    }

    Set<PermissionEntity> permissionsToAdd = new HashSet<>(permissionEntities);
    roleEntity.getPermissions().addAll(permissionsToAdd);

    roleEntity = this.roleRepository.save(roleEntity);

    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);
    return ResponseDto.success(roleResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> update(Long id, RoleUpdateDto roleUpdateDto) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);

    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    this.roleConverter.copyToRoleEntity(roleUpdateDto, roleEntity);
    roleEntity = this.roleRepository.save(roleEntity);

    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);
    return ResponseDto.success(roleResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> replacePermissions(Long id, RoleReplacePermissionsDto roleReplacePermissionsDto) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);
    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    List<PermissionEntity> permissionEntities = this.permissionRepository.findAllById(roleReplacePermissionsDto.getPermissionIds());
    if (permissionEntities.size() != roleReplacePermissionsDto.getPermissionIds().size()) {
      return ResponseDto.notFound("Some permissions not found");
    }

    Set<PermissionEntity> newPermissions = new HashSet<>(permissionEntities);
    roleEntity.setPermissions(newPermissions);

    roleEntity = this.roleRepository.save(roleEntity);
    
    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);
    return ResponseDto.success(roleResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);

    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    this.roleRepository.delete(roleEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> removePermissions(Long id, RoleRemovePermissionsDto roleRemovePermissionsDto) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);
    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    List<PermissionEntity> permissionEntities = this.permissionRepository.findAllById(roleRemovePermissionsDto.getPermissionIds());
    if (permissionEntities.size() != roleRemovePermissionsDto.getPermissionIds().size()) {
      return ResponseDto.notFound("Some permissions not found");
    }

    Set<PermissionEntity> permissionsToRemove = new HashSet<>(permissionEntities);
    roleEntity.getPermissions().removeAll(permissionsToRemove);

    roleEntity = this.roleRepository.save(roleEntity);

    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);
    return ResponseDto.success(roleResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<RoleResponseDto>>> find(RoleFindDto query,
      Pageable pageable) {
    Specification<RoleEntity> roleSpec = this.roleRepository.hasCriteria(query);

    Page<RoleEntity> rolePage = this.roleRepository.findAll(roleSpec, pageable);

    int page = rolePage.getNumber();
    int size = rolePage.getSize();
    int totalPages = rolePage.getTotalPages();
    List<RoleEntity> items = rolePage.getContent();

    List<RoleResponseDto> roleResponseDtos = items.stream()
        .map(item -> this.roleConverter.toRoleResponseDto(item)).toList();
    ResponseSpecification<RoleResponseDto> roleResponseSpecification = ResponseSpecification
        .<RoleResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(roleResponseDtos)
        .build();
    return ResponseDto.success(roleResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<RoleResponseDto>> findById(Long id) {
    RoleEntity roleEntity = this.roleRepository.findById(id).orElse(null);
    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }

    RoleResponseDto roleResponseDto = this.roleConverter.toRoleResponseDto(roleEntity);
    return ResponseDto.success(roleResponseDto);
  }
}
