package com.eduSocialMedia.dtos.chatRooms;

import com.eduSocialMedia.repositories.enums.ChatRoomTypeEnum;

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
public class ChatRoomFindDto {
  private String name;
  private ChatRoomTypeEnum type;
  private Long groupId;
  private String groupName;
  private Boolean isActive;
}
