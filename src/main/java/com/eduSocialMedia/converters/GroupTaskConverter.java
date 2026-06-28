package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.groupTasks.GroupTaskCreateDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskResponseDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskUpdateDto;
import com.eduSocialMedia.repositories.entities.GroupTaskEntity;

@Component
public class GroupTaskConverter {
  @Autowired
  private ModelMapper modelMapper;

  public GroupTaskEntity toGroupTaskEntity(GroupTaskCreateDto createDto) {
    return this.modelMapper.map(createDto, GroupTaskEntity.class);
  }

  public GroupTaskResponseDto toGroupTaskResponseDto(GroupTaskEntity entity) {
    return this.modelMapper.map(entity, GroupTaskResponseDto.class);
  }

  public GroupTaskEntity copyToGroupTaskEntity(GroupTaskUpdateDto updateDto, GroupTaskEntity entity) {
    this.modelMapper.map(updateDto, entity);
    return entity;
  }
}
