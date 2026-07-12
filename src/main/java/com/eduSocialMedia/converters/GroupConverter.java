package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.groups.GroupCreateDto;
import com.eduSocialMedia.dtos.groups.GroupResponseDto;
import com.eduSocialMedia.dtos.groups.GroupUpdateDto;
import com.eduSocialMedia.repositories.entities.GroupEntity;

@Component
public class GroupConverter {
  private final ModelMapper modelMapper;

  GroupConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public GroupEntity toGroupEntity(GroupCreateDto groupCreateDto) {
    return this.modelMapper.map(groupCreateDto, GroupEntity.class);
  }

  public GroupResponseDto toGroupResponseDto(GroupEntity groupEntity) {
    GroupResponseDto responseDto = this.modelMapper.map(groupEntity, GroupResponseDto.class);
    if (groupEntity.getGroupTopic() != null) {
      responseDto.setGroupTopicId(groupEntity.getGroupTopic().getId());
    }
    return responseDto;
  }

  public GroupEntity copyToGroupEntity(GroupUpdateDto groupUpdateDto, GroupEntity groupEntity) {
    this.modelMapper.map(groupUpdateDto, groupEntity);
    return groupEntity;
  }
}
