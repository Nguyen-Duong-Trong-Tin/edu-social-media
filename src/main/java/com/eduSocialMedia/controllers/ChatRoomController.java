package com.eduSocialMedia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomCreateDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomFindDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomResponseDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomUpdateDto;
import com.eduSocialMedia.services.ChatRoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/chat-rooms")
public class ChatRoomController {
  @Autowired
  private ChatRoomService chatRoomService;

  @PostMapping
  public ResponseEntity<ResponseDto<ChatRoomResponseDto>> create(@Valid @RequestBody ChatRoomCreateDto createDto) {
    return this.chatRoomService.create(createDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<ChatRoomResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody ChatRoomUpdateDto updateDto) {
    return this.chatRoomService.update(id, updateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.chatRoomService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<ChatRoomResponseDto>>> find(ChatRoomFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.chatRoomService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<ChatRoomResponseDto>> findById(@PathVariable Long id) {
    return this.chatRoomService.findById(id);
  }
}
