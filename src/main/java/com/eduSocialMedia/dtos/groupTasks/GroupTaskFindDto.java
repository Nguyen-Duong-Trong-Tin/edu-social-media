package com.eduSocialMedia.dtos.groupTasks;

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
public class GroupTaskFindDto {
  private String name;
  private Boolean isActive;
  private Long groupId;
  private String groupName;
}
