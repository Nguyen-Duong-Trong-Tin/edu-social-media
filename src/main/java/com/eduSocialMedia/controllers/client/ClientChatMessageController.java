package com.eduSocialMedia.controllers.client;

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

import com.eduSocialMedia.annotations.Auth;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageCreateDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageFindDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageResponseDto;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageUpdateDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.services.client.ClientChatMessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/chat-messages")
public class ClientChatMessageController {
  private final ClientChatMessageService chatMessageService;

  ClientChatMessageController(ClientChatMessageService chatMessageService) {
    this.chatMessageService = chatMessageService;
  }

  @PostMapping
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<ChatMessageResponseDto>> create(@Valid @RequestBody ChatMessageCreateDto createDto) {
    return this.chatMessageService.create(createDto);
  }

  @PatchMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<ChatMessageResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody ChatMessageUpdateDto updateDto) {
    return this.chatMessageService.update(id, updateDto);
  }

  @DeleteMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.chatMessageService.delete(id);
  }

  @GetMapping
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<ResponseSpecification<ChatMessageResponseDto>>> find(ChatMessageFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.chatMessageService.find(query, pageable);
  }

  @GetMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<ChatMessageResponseDto>> findById(@PathVariable Long id) {
    return this.chatMessageService.findById(id);
  }
}
