package com.eduSocialMedia.dtos.settingSidebarLinks;

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
public class SettingSidebarLinkFindDto {
  private String name;
  private String path;
  private String icon;
  private Boolean isExact;
  private Integer sortOrder;
}
