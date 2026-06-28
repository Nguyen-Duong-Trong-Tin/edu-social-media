package com.eduSocialMedia.dtos.groupTaskSubmissions;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskResponseDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupTaskSubmissionResponseDto extends BaseResponseDto {
  private String name;
  private String slug;
  private String description;
  private String images;
  private String videos;
  private String materials;
  private String comment;
  private Boolean isActive;
  private GroupTaskResponseDto groupTask;
  private UserResponseDto user;
}
