package com.eduSocialMedia.dtos.groupTaskSubmissions;

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
public class GroupTaskSubmissionCreateDto {
  @NotBlank(message = "Group task submission name is not blank")
  @Size(max = 255, message = "Group task submission name maximum length is 255 characters")
  private String name;

  private String description;

  private String comment;

  @NotNull(message = "Group task id is required")
  private Long groupTaskId;

  @NotNull(message = "User id is required")
  private Long userId;
}
