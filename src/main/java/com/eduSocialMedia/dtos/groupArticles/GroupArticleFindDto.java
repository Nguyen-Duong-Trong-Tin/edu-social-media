package com.eduSocialMedia.dtos.groupArticles;

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
public class GroupArticleFindDto {
  private String name;
  private Boolean isActive;
  private Long groupId;
  private String groupName;
}
