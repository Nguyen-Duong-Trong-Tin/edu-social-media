package com.eduSocialMedia.dtos.userArticles;

import com.eduSocialMedia.enums.UserArticleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client version of UserArticleUpdateDto: owners cannot change the owner or the admin-controlled isActive flag.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientUserArticleUpdateDto {
  private String content;

  private UserArticleStatusEnum status;
}
