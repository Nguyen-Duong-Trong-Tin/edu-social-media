package com.eduSocialMedia.dtos.groups;

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
public class GroupFindDto {
  private String name;
  private Boolean isActive;
  private Long groupTopicId;
  private String groupTopicName;
}
