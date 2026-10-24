package com.eduSocialMedia.dtos.userArticles;

import com.eduSocialMedia.enums.UserArticleStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UserArticleCreateDto {
  @NotBlank(message = "User article content is not blank")
  private String content;

  @NotNull(message = "User article status is required")
  private UserArticleStatusEnum status;

  @NotNull(message = "User id is required")
  private Long userId;
}
