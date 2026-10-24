package com.eduSocialMedia.dtos.userArticles;

import com.eduSocialMedia.enums.UserArticleStatusEnum;
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
public class UserArticleUpdateDto {
  private String content;

  private UserArticleStatusEnum status;

  private Boolean isActive;

  private Long userId;
}
