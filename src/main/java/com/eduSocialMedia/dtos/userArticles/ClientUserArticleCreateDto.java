package com.eduSocialMedia.dtos.userArticles;

import com.eduSocialMedia.enums.UserArticleStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client version of UserArticleCreateDto: the owner is the logged-in user, never taken from the body.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientUserArticleCreateDto {
  @NotBlank(message = "User article content is not blank")
  private String content;

  @NotNull(message = "User article status is required")
  private UserArticleStatusEnum status;
}
