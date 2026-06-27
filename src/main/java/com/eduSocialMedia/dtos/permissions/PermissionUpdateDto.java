package com.eduSocialMedia.dtos.permissions;

import jakarta.validation.constraints.Size;
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
public class PermissionUpdateDto {
  @Size(max = 100, message = "Name maximum length is 100 characters")
  private String name;

  @Size(max = 100, message = "Category maximum length is 100 characters")
  private String category;
}
