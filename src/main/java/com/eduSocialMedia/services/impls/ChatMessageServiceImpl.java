package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.ChatMessageConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageCreateDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageFindDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageResponseDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageUpdateDto;
import com.eduSocialMedia.repositories.ChatMessageRepository;
import com.eduSocialMedia.repositories.ChatRoomRepository;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.ChatMessageEntity;
import com.eduSocialMedia.repositories.entities.ChatRoomEntity;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.ChatMessageService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
  @Autowired
  private ChatMessageConverter chatMessageConverter;

  @Autowired
  private ChatMessageRepository chatMessageRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @Override
  public ResponseEntity<ResponseDto<ChatMessageResponseDto>> create(ChatMessageCreateDto createDto) {
    UserEntity user = this.userRepository.findByIdAndIsDeletedFalse(createDto.getUserId()).orElse(null);
    if (user == null) {
      return ResponseDto.notFound("User not found");
    }

    ChatRoomEntity chatRoom = this.chatRoomRepository.findByIdAndIsDeletedFalse(createDto.getChatRoomId()).orElse(null);
    if (chatRoom == null) {
      return ResponseDto.notFound("Chat room not found");
    }

    ChatMessageEntity entity = this.chatMessageConverter.toChatMessageEntity(createDto);
    entity.setUser(user);
    entity.setChatRoom(chatRoom);

    entity = this.chatMessageRepository.save(entity);

    ChatMessageResponseDto responseDto = this.chatMessageConverter.toChatMessageResponseDto(entity);
    return ResponseDto.created(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<ChatMessageResponseDto>> update(Long id, ChatMessageUpdateDto updateDto) {
    ChatMessageEntity entity = this.chatMessageRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Chat message not found");
    }

    if (updateDto.getUserId() != null) {
      UserEntity user = this.userRepository.findByIdAndIsDeletedFalse(updateDto.getUserId()).orElse(null);
      if (user == null) {
        return ResponseDto.notFound("User not found");
      }
      entity.setUser(user);
    }

    if (updateDto.getChatRoomId() != null) {
      ChatRoomEntity chatRoom = this.chatRoomRepository.findByIdAndIsDeletedFalse(updateDto.getChatRoomId()).orElse(null);
      if (chatRoom == null) {
        return ResponseDto.notFound("Chat room not found");
      }
      entity.setChatRoom(chatRoom);
    }

    this.chatMessageConverter.copyToChatMessageEntity(updateDto, entity);

    entity = this.chatMessageRepository.save(entity);

    ChatMessageResponseDto responseDto = this.chatMessageConverter.toChatMessageResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    ChatMessageEntity entity = this.chatMessageRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Chat message not found");
    }

    entity.setIsDeleted(true);
    this.chatMessageRepository.save(entity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<ChatMessageResponseDto>>> find(ChatMessageFindDto query, Pageable pageable) {
    Specification<ChatMessageEntity> spec = this.chatMessageRepository.hasCriteria(query);
    Page<ChatMessageEntity> pageResult = this.chatMessageRepository.findAll(spec, pageable);

    int page = pageResult.getNumber();
    int size = pageResult.getSize();
    int totalPages = pageResult.getTotalPages();
    List<ChatMessageEntity> items = pageResult.getContent();

    List<ChatMessageResponseDto> responseDtos = items.stream()
        .map(item -> this.chatMessageConverter.toChatMessageResponseDto(item)).toList();

    ResponseSpecification<ChatMessageResponseDto> specResult = ResponseSpecification
        .<ChatMessageResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(responseDtos)
        .build();

    return ResponseDto.success(specResult);
  }

  @Override
  public ResponseEntity<ResponseDto<ChatMessageResponseDto>> findById(Long id) {
    ChatMessageEntity entity = this.chatMessageRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Chat message not found");
    }

    ChatMessageResponseDto responseDto = this.chatMessageConverter.toChatMessageResponseDto(entity);
    return ResponseDto.success(responseDto);
  }
}
