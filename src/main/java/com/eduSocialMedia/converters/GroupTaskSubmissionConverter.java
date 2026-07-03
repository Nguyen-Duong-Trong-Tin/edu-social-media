package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionCreateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionResponseDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionUpdateDto;
import com.eduSocialMedia.repositories.entities.GroupTaskSubmissionEntity;

@Component
public class GroupTaskSubmissionConverter {
  private final ModelMapper modelMapper;

  GroupTaskSubmissionConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public GroupTaskSubmissionEntity toGroupTaskSubmissionEntity(GroupTaskSubmissionCreateDto createDto) {
    return this.modelMapper.map(createDto, GroupTaskSubmissionEntity.class);
  }

  public GroupTaskSubmissionResponseDto toGroupTaskSubmissionResponseDto(GroupTaskSubmissionEntity entity) {
    return this.modelMapper.map(entity, GroupTaskSubmissionResponseDto.class);
  }

  public GroupTaskSubmissionEntity copyToGroupTaskSubmissionEntity(GroupTaskSubmissionUpdateDto updateDto, GroupTaskSubmissionEntity entity) {
    this.modelMapper.map(updateDto, entity);
    return entity;
  }
}
