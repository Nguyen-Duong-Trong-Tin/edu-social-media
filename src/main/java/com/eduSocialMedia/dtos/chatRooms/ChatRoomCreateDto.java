package com.eduSocialMedia.dtos.chatRooms;

import com.eduSocialMedia.repositories.enums.ChatRoomTypeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ChatRoomCreateDto {
  @NotBlank(message = "Chat room name is not blank")
  @Size(max = 255, message = "Chat room name maximum length is 255 characters")
  private String name;

  @NotNull(message = "Chat room type is required")
  private ChatRoomTypeEnum type;

  private Long groupId;
}
