package com.eduSocialMedia.dtos.groupTopics;

import com.eduSocialMedia.dtos.BaseResponseDto;
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
public class GroupTopicResponseDto extends BaseResponseDto {
  private String name;
  private String slug;
  private String description;
}
