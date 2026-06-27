package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkCreateDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkResponseDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkUpdateDto;
import com.eduSocialMedia.repositories.entities.SettingSidebarLinkEntity;

@Component
public class SettingSidebarLinkConverter {
  @Autowired
  private ModelMapper modelMapper;

  public SettingSidebarLinkEntity toSettingSidebarLinkEntity(SettingSidebarLinkCreateDto dto) {
    return this.modelMapper.map(dto, SettingSidebarLinkEntity.class);
  }

  public SettingSidebarLinkResponseDto toSettingSidebarLinkResponseDto(SettingSidebarLinkEntity entity) {
    return this.modelMapper.map(entity, SettingSidebarLinkResponseDto.class);
  }

  public SettingSidebarLinkEntity copyToSettingSidebarLinkEntity(SettingSidebarLinkUpdateDto dto, SettingSidebarLinkEntity entity) {
    this.modelMapper.map(dto, entity);
    return entity;
  }
}
