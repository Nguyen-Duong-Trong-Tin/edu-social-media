package com.eduSocialMedia.dtos.auth;

import com.eduSocialMedia.dtos.users.UserResponseDto;

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
public class AuthUserLoginResponseDto {
  private String accessToken;
  private String refreshToken;
  private UserResponseDto user;
}
