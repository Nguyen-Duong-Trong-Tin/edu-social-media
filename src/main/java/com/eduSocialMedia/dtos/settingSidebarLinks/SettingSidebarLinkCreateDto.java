package com.eduSocialMedia.dtos.settingSidebarLinks;

import jakarta.validation.constraints.NotBlank;
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
public class SettingSidebarLinkCreateDto {
  @NotBlank(message = "Name is not blank")
  @Size(max = 50, message = "Name maximum length is 50 characters")
  private String name;

  @NotBlank(message = "Path is not blank")
  @Size(max = 50, message = "Path maximum length is 50 characters")
  private String path;

  @NotBlank(message = "Icon is not blank")
  @Size(max = 50, message = "Icon maximum length is 50 characters")
  private String icon;

  @Builder.Default
  private Boolean isExact = false;

  private Integer sortOrder;
}
