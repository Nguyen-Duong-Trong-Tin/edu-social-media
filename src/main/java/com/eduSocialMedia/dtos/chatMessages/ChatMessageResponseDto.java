package com.eduSocialMedia.dtos.chatMessages;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomResponseDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;

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
  private UserResponseDto user;
  private ChatRoomResponseDto chatRoom;
}
