package com.eduSocialMedia.dtos.chatMessages;

import jakarta.validation.constraints.NotNull;
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
public class ChatMessageCreateDto {
  private String content;

  @NotNull(message = "User id is required")
  private Long userId;

  @NotNull(message = "Chat room id is required")
  private Long chatRoomId;
}
