package com.eduSocialMedia.services.admin.impls;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.ChatRoomConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomCreateDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomFindDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomResponseDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomUpdateDto;
import com.eduSocialMedia.repositories.ChatRoomRepository;
import com.eduSocialMedia.repositories.GroupRepository;
import com.eduSocialMedia.repositories.entities.ChatRoomEntity;
import com.eduSocialMedia.repositories.entities.GroupEntity;
import com.eduSocialMedia.services.admin.AdminChatRoomService;

@Service("adminChatRoomServiceImpl")
public class AdminChatRoomServiceImpl implements AdminChatRoomService {
  private final ChatRoomConverter chatRoomConverter;
  private final ChatRoomRepository chatRoomRepository;
  private final GroupRepository groupRepository;

  AdminChatRoomServiceImpl(ChatRoomConverter chatRoomConverter, ChatRoomRepository chatRoomRepository,
      GroupRepository groupRepository) {
    this.chatRoomConverter = chatRoomConverter;
    this.chatRoomRepository = chatRoomRepository;
    this.groupRepository = groupRepository;
  }

  private String generateSlug(String name) {
    if (name == null) {
      return "";
    }
    String temp = name.toLowerCase().trim()
        .replaceAll("[^a-z0-9\\s-]", "")
        .replaceAll("[\\s-]+", "-");
    if (temp.startsWith("-")) {
      temp = temp.substring(1);
    }
    if (temp.endsWith("-")) {
      temp = temp.substring(0, temp.length() - 1);
    }
    return temp;
  }

  private String generateUniqueSlug(String name) {
    String baseSlug = generateSlug(name);
    String slug = baseSlug;
    int counter = 1;
    while (this.chatRoomRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }

  @Override
  public ResponseEntity<ResponseDto<ChatRoomResponseDto>> create(ChatRoomCreateDto createDto) {
    GroupEntity groupEntity = null;
    if (createDto.getGroupId() != null) {
      groupEntity = this.groupRepository.findByIdAndIsDeletedFalse(createDto.getGroupId()).orElse(null);
      if (groupEntity == null) {
        return ResponseDto.notFound("Group not found");
      }
    }

    ChatRoomEntity ChatRoomEntity = this.chatRoomConverter.toChatRoomEntity(createDto);
    ChatRoomEntity.setGroup(groupEntity);
    ChatRoomEntity.setSlug(generateUniqueSlug(createDto.getName()));

    ChatRoomEntity = this.chatRoomRepository.save(ChatRoomEntity);

    ChatRoomResponseDto responseDto = this.chatRoomConverter.toChatRoomResponseDto(ChatRoomEntity);
    return ResponseDto.created(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<ChatRoomResponseDto>> update(Long id, ChatRoomUpdateDto updateDto) {
    ChatRoomEntity chatRoomEntity = this.chatRoomRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (chatRoomEntity == null) {
      return ResponseDto.notFound("Chat room not found");
    }

    if (updateDto.getName() != null && !updateDto.getName().trim().isEmpty()
        && !updateDto.getName().equals(chatRoomEntity.getName())) {
      chatRoomEntity.setSlug(generateUniqueSlug(updateDto.getName()));
    }

    if (updateDto.getGroupId() != null) {
      GroupEntity groupEntity = this.groupRepository.findByIdAndIsDeletedFalse(updateDto.getGroupId()).orElse(null);
      if (groupEntity == null) {
        return ResponseDto.notFound("Group not found");
      }
      chatRoomEntity.setGroup(groupEntity);
    }

    this.chatRoomConverter.copyToChatRoomEntity(updateDto, chatRoomEntity);

    chatRoomEntity = this.chatRoomRepository.save(chatRoomEntity);

    ChatRoomResponseDto responseDto = this.chatRoomConverter.toChatRoomResponseDto(chatRoomEntity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    ChatRoomEntity chatRoomEntity = this.chatRoomRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (chatRoomEntity == null) {
      return ResponseDto.notFound("Chat room not found");
    }

    chatRoomEntity.setIsDeleted(true);
    this.chatRoomRepository.save(chatRoomEntity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<ChatRoomResponseDto>>> find(ChatRoomFindDto query,
      Pageable pageable) {
    Specification<ChatRoomEntity> spec = this.chatRoomRepository.hasCriteria(query);
    Page<ChatRoomEntity> pageResult = this.chatRoomRepository.findAll(spec, pageable);

    int page = pageResult.getNumber();
    int size = pageResult.getSize();
    int totalPages = pageResult.getTotalPages();
    List<ChatRoomEntity> items = pageResult.getContent();

    List<ChatRoomResponseDto> responseDtos = items.stream()
        .map(item -> this.chatRoomConverter.toChatRoomResponseDto(item)).toList();

    ResponseSpecification<ChatRoomResponseDto> specResult = ResponseSpecification
        .<ChatRoomResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(responseDtos)
        .build();

    return ResponseDto.success(specResult);
  }

  @Override
  public ResponseEntity<ResponseDto<ChatRoomResponseDto>> findById(Long id) {
    ChatRoomEntity chatRoomEntity = this.chatRoomRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (chatRoomEntity == null) {
      return ResponseDto.notFound("Chat room not found");
    }

    ChatRoomResponseDto responseDto = this.chatRoomConverter.toChatRoomResponseDto(chatRoomEntity);
    return ResponseDto.success(responseDto);
  }
}
