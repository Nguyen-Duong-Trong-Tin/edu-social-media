package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.groupArticles.GroupArticleCreateDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleResponseDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleUpdateDto;
import com.eduSocialMedia.repositories.entities.GroupArticleEntity;

@Component
public class GroupArticleConverter {
  @Autowired
  private ModelMapper modelMapper;

  public GroupArticleEntity toGroupArticleEntity(GroupArticleCreateDto createDto) {
    return this.modelMapper.map(createDto, GroupArticleEntity.class);
  }

  public GroupArticleResponseDto toGroupArticleResponseDto(GroupArticleEntity entity) {
    return this.modelMapper.map(entity, GroupArticleResponseDto.class);
  }

  public GroupArticleEntity copyToGroupArticleEntity(GroupArticleUpdateDto updateDto, GroupArticleEntity entity) {
    this.modelMapper.map(updateDto, entity);
    return entity;
  }
}
