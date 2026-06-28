package com.eduSocialMedia.dtos.userArticles;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
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
public class UserArticleResponseDto extends BaseResponseDto {
  private String name;
  private String slug;
  private String description;
  private String images;
  private String videos;
  private Boolean isActive;
  private UserResponseDto user;
}
