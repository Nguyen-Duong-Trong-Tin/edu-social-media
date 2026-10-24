package com.eduSocialMedia.services.client;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageCreateDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageFindDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageResponseDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageUpdateDto;

public interface ClientChatMessageService {
  ResponseEntity<ResponseDto<ChatMessageResponseDto>> create(ChatMessageCreateDto createDto);

  ResponseEntity<ResponseDto<ChatMessageResponseDto>> update(Long id, ChatMessageUpdateDto updateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<ChatMessageResponseDto>>> find(ChatMessageFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<ChatMessageResponseDto>> findById(Long id);
}
