package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.groupTopics.GroupTopicCreateDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicResponseDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicUpdateDto;
import com.eduSocialMedia.repositories.entities.GroupTopicEntity;

@Component
public class GroupTopicConverter {
  @Autowired
  private ModelMapper modelMapper;

  public GroupTopicEntity toGroupTopicEntity(GroupTopicCreateDto groupTopicCreateDto) {
    return this.modelMapper.map(groupTopicCreateDto, GroupTopicEntity.class);
  }

  public GroupTopicResponseDto toGroupTopicResponseDto(GroupTopicEntity groupTopicEntity) {
    return this.modelMapper.map(groupTopicEntity, GroupTopicResponseDto.class);
  }

  public GroupTopicEntity copyToGroupTopicEntity(GroupTopicUpdateDto groupTopicUpdateDto, GroupTopicEntity groupTopicEntity) {
    this.modelMapper.map(groupTopicUpdateDto, groupTopicEntity);
    return groupTopicEntity;
  }
}
