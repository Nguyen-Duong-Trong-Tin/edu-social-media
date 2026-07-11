package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.chatMessages.ChatMessageCreateDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageResponseDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageUpdateDto;
import com.eduSocialMedia.repositories.entities.ChatMessageEntity;

@Component
public class ChatMessageConverter {
  private final ModelMapper modelMapper;

  ChatMessageConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public ChatMessageEntity toChatMessageEntity(ChatMessageCreateDto createDto) {
    return this.modelMapper.map(createDto, ChatMessageEntity.class);
  }

  public ChatMessageResponseDto toChatMessageResponseDto(ChatMessageEntity entity) {
    ChatMessageResponseDto responseDto = this.modelMapper.map(entity, ChatMessageResponseDto.class);
    if (entity.getUser() != null) {
      responseDto.setUserId(String.valueOf(entity.getUser().getId()));
    }
    if (entity.getChatRoom() != null) {
      responseDto.setChatRoomId(String.valueOf(entity.getChatRoom().getId()));
    }
    return responseDto;
  }

  public ChatMessageEntity copyToChatMessageEntity(ChatMessageUpdateDto updateDto, ChatMessageEntity entity) {
    this.modelMapper.map(updateDto, entity);
    return entity;
  }
}
