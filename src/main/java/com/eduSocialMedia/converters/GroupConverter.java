package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.groups.GroupCreateDto;
import com.eduSocialMedia.dtos.groups.GroupResponseDto;
import com.eduSocialMedia.dtos.groups.GroupUpdateDto;
import com.eduSocialMedia.repositories.entities.GroupEntity;

@Component
public class GroupConverter {
  @Autowired
  private ModelMapper modelMapper;

  public GroupEntity toGroupEntity(GroupCreateDto groupCreateDto) {
    return this.modelMapper.map(groupCreateDto, GroupEntity.class);
  }

  public GroupResponseDto toGroupResponseDto(GroupEntity groupEntity) {
    return this.modelMapper.map(groupEntity, GroupResponseDto.class);
  }

  public GroupEntity copyToGroupEntity(GroupUpdateDto groupUpdateDto, GroupEntity groupEntity) {
    this.modelMapper.map(groupUpdateDto, groupEntity);
    return groupEntity;
  }
}
