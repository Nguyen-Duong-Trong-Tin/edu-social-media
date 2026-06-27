package com.eduSocialMedia.dtos.groupTopics;

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
public class GroupTopicUpdateDto {
  @Size(max = 100, message = "Group topic name maximum length is 100 characters")
  private String name;

  private String description;
}
