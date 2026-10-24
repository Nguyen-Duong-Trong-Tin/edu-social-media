package com.eduSocialMedia.services.admin;

import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.accounts.AccountResponseDto;
import com.eduSocialMedia.dtos.auth.AuthLoginDto;
import com.eduSocialMedia.dtos.auth.AuthLoginResponseDto;
import com.eduSocialMedia.dtos.auth.AuthRefreshTokenDto;
import com.eduSocialMedia.dtos.auth.AuthRefreshTokenResponseDto;

public interface AdminAuthService {
  ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(AuthLoginDto authLoginDto);

  ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(AuthRefreshTokenDto authRefreshTokenDto);

  ResponseEntity<ResponseDto<AccountResponseDto>> me();

  ResponseEntity<ResponseDto<Boolean>> verifyToken();
}