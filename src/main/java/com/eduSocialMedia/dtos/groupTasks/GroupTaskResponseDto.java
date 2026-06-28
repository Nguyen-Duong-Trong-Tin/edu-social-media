package com.eduSocialMedia.dtos.groupTasks;

import java.sql.Date;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.groups.GroupResponseDto;
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
public class GroupTaskResponseDto extends BaseResponseDto {
  private String name;
  private String slug;
  private String description;
  private String images;
  private String videos;
  private Date deadline;
  private Boolean isActive;
  private GroupResponseDto group;
}
