package com.eduSocialMedia.dtos.groups;

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
public class GroupCreateDto {
  @NotBlank(message = "Group name is not blank")
  @Size(max = 255, message = "Group name maximum length is 255 characters")
  private String name;

  private String description;

  private String invitation;

  @NotNull(message = "Group topic id is required")
  private Long groupTopicId;
}
