package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.chatMessages.ChatMessageCreateDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageResponseDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageUpdateDto;
import com.eduSocialMedia.repositories.entities.ChatMessageEntity;

@Component
public class ChatMessageConverter {
  @Autowired
  private ModelMapper modelMapper;

  public ChatMessageEntity toChatMessageEntity(ChatMessageCreateDto createDto) {
    return this.modelMapper.map(createDto, ChatMessageEntity.class);
  }

  public ChatMessageResponseDto toChatMessageResponseDto(ChatMessageEntity entity) {
    return this.modelMapper.map(entity, ChatMessageResponseDto.class);
  }

  public ChatMessageEntity copyToChatMessageEntity(ChatMessageUpdateDto updateDto, ChatMessageEntity entity) {
    this.modelMapper.map(updateDto, entity);
    return entity;
  }
}
