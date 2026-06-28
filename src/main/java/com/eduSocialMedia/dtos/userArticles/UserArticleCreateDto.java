package com.eduSocialMedia.dtos.userArticles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
  @NotBlank(message = "User article name is not blank")
  @Size(max = 255, message = "User article name maximum length is 255 characters")
  private String name;

  private String description;

  @NotNull(message = "User id is required")
  private Long userId;
}
