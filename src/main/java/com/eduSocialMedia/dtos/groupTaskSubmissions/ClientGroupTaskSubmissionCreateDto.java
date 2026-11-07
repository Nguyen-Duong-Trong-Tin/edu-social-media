package com.eduSocialMedia.dtos.groupTaskSubmissions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client version of GroupTaskSubmissionCreateDto: the owner is the logged-in user, never taken from the body.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientGroupTaskSubmissionCreateDto {
  @NotBlank(message = "Group task submission name is not blank")
  @Size(max = 255, message = "Group task submission name maximum length is 255 characters")
  private String name;

  private String description;

  private String comment;

  @NotNull(message = "Group task id is required")
  private Long groupTaskId;
}
