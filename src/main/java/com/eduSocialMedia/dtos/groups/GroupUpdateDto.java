package com.eduSocialMedia.dtos.groups;

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
public class GroupUpdateDto {
  @Size(max = 255, message = "Group name maximum length is 255 characters")
  private String name;

  private String description;

  private String invitation;

  private Boolean isActive;

  private Long groupTopicId;
}
