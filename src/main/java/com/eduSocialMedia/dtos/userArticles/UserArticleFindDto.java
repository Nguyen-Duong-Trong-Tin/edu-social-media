package com.eduSocialMedia.dtos.userArticles;

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
public class UserArticleFindDto {
  private String name;
  private Boolean isActive;
  private Long userId;
  private String userName;
}
