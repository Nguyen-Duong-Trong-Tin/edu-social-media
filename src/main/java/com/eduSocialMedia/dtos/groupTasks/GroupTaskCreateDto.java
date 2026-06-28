package com.eduSocialMedia.dtos.groupTasks;

import java.sql.Date;

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
public class GroupTaskCreateDto {
  @NotBlank(message = "Group task name is not blank")
  @Size(max = 255, message = "Group task name maximum length is 255 characters")
  private String name;

  private String description;

  private Date deadline;

  @NotNull(message = "Group id is required")
  private Long groupId;
}
