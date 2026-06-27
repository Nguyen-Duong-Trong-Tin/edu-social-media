package com.eduSocialMedia.dtos.groups;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicResponseDto;
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
public class GroupResponseDto extends BaseResponseDto {
  private String name;
  private String slug;
  private String description;
  private String invitation;
  private String avatar;
  private String coverPhoto;
  private Boolean isActive;
  private GroupTopicResponseDto groupTopic;
}
