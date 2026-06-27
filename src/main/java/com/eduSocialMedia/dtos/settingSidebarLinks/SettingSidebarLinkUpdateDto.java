package com.eduSocialMedia.dtos.settingSidebarLinks;

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
public class SettingSidebarLinkUpdateDto {
  @Size(max = 50, message = "Name maximum length is 50 characters")
  private String name;

  @Size(max = 50, message = "Path maximum length is 50 characters")
  private String path;

  @Size(max = 50, message = "Icon maximum length is 50 characters")
  private String icon;

  private Boolean isExact;

  private Integer sortOrder;
}
