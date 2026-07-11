package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.chatRooms.ChatRoomCreateDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomResponseDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomUpdateDto;
import com.eduSocialMedia.repositories.entities.ChatRoomEntity;

@Component
public class ChatRoomConverter {
  private final ModelMapper modelMapper;

  ChatRoomConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public ChatRoomEntity toChatRoomEntity(ChatRoomCreateDto createDto) {
    return this.modelMapper.map(createDto, ChatRoomEntity.class);
  }

  public ChatRoomResponseDto toChatRoomResponseDto(ChatRoomEntity entity) {
    ChatRoomResponseDto responseDto = this.modelMapper.map(entity, ChatRoomResponseDto.class);
    if (entity.getGroup() != null) {
      responseDto.setGroupId(entity.getGroup().getId());
    }
    return responseDto;
  }

  public ChatRoomEntity copyToChatRoomEntity(ChatRoomUpdateDto updateDto, ChatRoomEntity entity) {
    this.modelMapper.map(updateDto, entity);
    return entity;
  }
}
