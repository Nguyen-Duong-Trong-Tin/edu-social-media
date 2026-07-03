package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.roles.RoleCreateDto;
import com.eduSocialMedia.dtos.roles.RoleResponseDto;
import com.eduSocialMedia.dtos.roles.RoleUpdateDto;
import com.eduSocialMedia.repositories.entities.RoleEntity;

@Component
public class RoleConverter {
  private final ModelMapper modelMapper;

  RoleConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public RoleEntity toRoleEntity(RoleCreateDto roleCreateDto) {
    return this.modelMapper.map(roleCreateDto, RoleEntity.class);
  }

  public RoleResponseDto toRoleResponseDto(RoleEntity roleEntity) {
    return this.modelMapper.map(roleEntity, RoleResponseDto.class);
  }

  public RoleEntity copyToRoleEntity(RoleUpdateDto roleUpdateDto, RoleEntity roleEntity) {
    this.modelMapper.map(roleUpdateDto, roleEntity);
    return roleEntity;
  }
}