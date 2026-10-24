package com.eduSocialMedia.services.client;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomCreateDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomFindDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomResponseDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomUpdateDto;

public interface ClientChatRoomService {
  ResponseEntity<ResponseDto<ChatRoomResponseDto>> create(ChatRoomCreateDto createDto);

  ResponseEntity<ResponseDto<ChatRoomResponseDto>> update(Long id, ChatRoomUpdateDto updateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<ChatRoomResponseDto>>> find(ChatRoomFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<ChatRoomResponseDto>> findById(Long id);
}
