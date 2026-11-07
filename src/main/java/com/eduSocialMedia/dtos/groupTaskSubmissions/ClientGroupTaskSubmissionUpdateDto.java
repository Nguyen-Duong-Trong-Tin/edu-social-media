package com.eduSocialMedia.dtos.groupTaskSubmissions;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client version of GroupTaskSubmissionUpdateDto: owners cannot change the owner or the admin-controlled isActive flag.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientGroupTaskSubmissionUpdateDto {
  @Size(max = 255, message = "Group task submission name maximum length is 255 characters")
  private String name;

  private String description;

  private String comment;

  private Long groupTaskId;
}
