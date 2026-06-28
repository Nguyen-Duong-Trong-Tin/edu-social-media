package com.eduSocialMedia.dtos.groupTaskSubmissions;

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
public class GroupTaskSubmissionFindDto {
  private String name;
  private Boolean isActive;
  private Long groupTaskId;
  private String groupTaskName;
  private Long userId;
  private String userName;
}
