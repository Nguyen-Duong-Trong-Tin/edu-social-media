package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.permissions.PermissionCreateDto;
import com.eduSocialMedia.dtos.permissions.PermissionResponseDto;
import com.eduSocialMedia.dtos.permissions.PermissionUpdateDto;
import com.eduSocialMedia.repositories.entities.PermissionEntity;

@Component
public class PermissionConverter {
  private final ModelMapper modelMapper;

  PermissionConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public PermissionEntity toPermissionEntity(PermissionCreateDto permissionCreateDto) {
    return this.modelMapper.map(permissionCreateDto, PermissionEntity.class);
  }

  public PermissionResponseDto toPermissionResponseDto(PermissionEntity permissionEntity) {
    return this.modelMapper.map(permissionEntity, PermissionResponseDto.class);
  }

  public PermissionEntity copyToPermissionEntity(PermissionUpdateDto permissionUpdateDto, PermissionEntity permissionEntity) {
    this.modelMapper.map(permissionUpdateDto, permissionEntity);
    return permissionEntity;
  }
}