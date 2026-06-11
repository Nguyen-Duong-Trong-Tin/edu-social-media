package com.eduSocialMedia.dtos.roles;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleAddPermissionsDto {
  @NotEmpty(message = "PermissionIds cannot be empty")
  private Set<@NotNull(message = "Permission ID cannot be null") Long> permissionIds;
}
