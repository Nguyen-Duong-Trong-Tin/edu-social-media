package com.eduSocialMedia.dtos.userArticles;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.enums.UserArticleStatusEnum;
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
  private String content;
  private String images;
  private String videos;
  private UserArticleStatusEnum status;
  private Boolean isActive;
  private Long userId;
}
