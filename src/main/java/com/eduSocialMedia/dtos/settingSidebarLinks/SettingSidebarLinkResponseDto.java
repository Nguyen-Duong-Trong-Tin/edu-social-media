package com.eduSocialMedia.dtos.settingSidebarLinks;

import com.eduSocialMedia.dtos.BaseResponseDto;
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
public class SettingSidebarLinkResponseDto extends BaseResponseDto {
  private String name;
  private String path;
  private String icon;
  private Boolean isExact;
  private Integer sortOrder;
}
