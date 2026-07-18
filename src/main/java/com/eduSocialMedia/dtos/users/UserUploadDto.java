package com.eduSocialMedia.dtos.users;

import com.eduSocialMedia.enums.UserUploadTypeEnum;

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
public class UserUploadDto {
  @NotNull(message = "User id is required")
  private Long userId;

  @NotNull(message = "Type is required")
  private UserUploadTypeEnum type;
}
