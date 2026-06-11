package com.eduSocialMedia.dtos.permissions;

import java.util.Set;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.repositories.entities.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PermissionResponseDto extends BaseResponseDto {
  private String name;
  private String code;
  @JsonIgnoreProperties("permissions")
  private Set<RoleEntity> roles;
}
