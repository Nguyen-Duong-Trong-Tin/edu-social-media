package com.eduSocialMedia.dtos.groupArticles;

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
public class GroupArticleUpdateDto {
  @Size(max = 255, message = "Group article name maximum length is 255 characters")
  private String name;

  private String description;

  private Boolean isActive;

  private Long groupId;
}
