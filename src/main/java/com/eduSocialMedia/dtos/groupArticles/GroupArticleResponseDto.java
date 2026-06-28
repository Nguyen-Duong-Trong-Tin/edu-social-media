package com.eduSocialMedia.dtos.groupArticles;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.groups.GroupResponseDto;
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
public class GroupArticleResponseDto extends BaseResponseDto {
  private String name;
  private String slug;
  private String description;
  private String images;
  private String videos;
  private Boolean isActive;
  private GroupResponseDto group;
}
