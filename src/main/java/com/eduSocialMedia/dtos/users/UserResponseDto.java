package com.eduSocialMedia.dtos.users;

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
public class UserResponseDto extends BaseResponseDto {
  private String fullName;
  private String slug;
  private String email;
  private String avatar;
  private String coverPhoto;
  private String bio;
  private Boolean isActive;
}
