package com.eduSocialMedia.services;

import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.auths.AuthLoginDto;
import com.eduSocialMedia.dtos.auths.AuthLoginResponseDto;

public interface AuthService {
  ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(AuthLoginDto authLoginDto);
}
