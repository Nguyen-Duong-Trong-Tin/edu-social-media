package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.userArticles.UserArticleCreateDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleResponseDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleUpdateDto;
import com.eduSocialMedia.repositories.entities.UserArticleEntity;

@Component
public class UserArticleConverter {
  @Autowired
  private ModelMapper modelMapper;

  public UserArticleEntity toUserArticleEntity(UserArticleCreateDto createDto) {
    return this.modelMapper.map(createDto, UserArticleEntity.class);
  }

  public UserArticleResponseDto toUserArticleResponseDto(UserArticleEntity entity) {
    return this.modelMapper.map(entity, UserArticleResponseDto.class);
  }

  public UserArticleEntity copyToUserArticleEntity(UserArticleUpdateDto updateDto, UserArticleEntity entity) {
    this.modelMapper.map(updateDto, entity);
    return entity;
  }
}
