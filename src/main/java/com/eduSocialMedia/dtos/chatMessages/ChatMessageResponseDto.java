package com.eduSocialMedia.dtos.chatMessages;

import com.eduSocialMedia.dtos.BaseResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageResponseDto extends BaseResponseDto {
  private String content;
  private String images;
  private String videos;
  private String materials;
  private String userId;
  private String chatRoomId;
}
