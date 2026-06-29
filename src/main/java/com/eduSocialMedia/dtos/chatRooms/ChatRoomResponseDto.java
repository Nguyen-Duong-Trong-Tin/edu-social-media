package com.eduSocialMedia.dtos.chatRooms;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.groups.GroupResponseDto;
import com.eduSocialMedia.repositories.enums.ChatRoomTypeEnum;

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
public class ChatRoomResponseDto extends BaseResponseDto {
  private String name;
  private String slug;
  private ChatRoomTypeEnum type;
  private String avatar;
  private Boolean isActive;
  private GroupResponseDto group;
}
