package com.eduSocialMedia.dtos.chatMessages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageUpdateDto {
  private String content;

  private Long userId;

  private Long chatRoomId;
}
