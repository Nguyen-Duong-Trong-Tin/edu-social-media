package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.chatRooms.ChatRoomCreateDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomResponseDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomUpdateDto;
import com.eduSocialMedia.repositories.entities.ChatRoomEntity;

@Component
public class ChatRoomConverter {
  @Autowired
  private ModelMapper modelMapper;

  public ChatRoomEntity toChatRoomEntity(ChatRoomCreateDto createDto) {
    return this.modelMapper.map(createDto, ChatRoomEntity.class);
  }

  public ChatRoomResponseDto toChatRoomResponseDto(ChatRoomEntity entity) {
    return this.modelMapper.map(entity, ChatRoomResponseDto.class);
  }

  public ChatRoomEntity copyToChatRoomEntity(ChatRoomUpdateDto updateDto, ChatRoomEntity entity) {
    this.modelMapper.map(updateDto, entity);
    return entity;
  }
}
