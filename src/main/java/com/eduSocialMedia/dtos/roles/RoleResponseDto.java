package com.eduSocialMedia.dtos.roles;

import java.util.Set;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.permissions.PermissionResponseDto;
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
public class RoleResponseDto extends BaseResponseDto {
  private String name;
  private String description;
  @JsonIgnoreProperties("roles")
  private Set<PermissionResponseDto> permissions;
}