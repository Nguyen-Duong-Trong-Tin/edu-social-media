package com.eduSocialMedia.services;

import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.accounts.AccountResponseDto;
import com.eduSocialMedia.dtos.auth.AuthLoginDto;
import com.eduSocialMedia.dtos.auth.AuthLoginResponseDto;
import com.eduSocialMedia.dtos.auth.AuthRefreshTokenDto;
import com.eduSocialMedia.dtos.auth.AuthRefreshTokenResponseDto;
import com.eduSocialMedia.dtos.auth.AuthRegisterDto;
import com.eduSocialMedia.dtos.auth.AuthRegisterResponseDto;
import com.eduSocialMedia.dtos.auth.AuthUserLoginDto;
import com.eduSocialMedia.dtos.auth.AuthUserLoginResponseDto;

public interface AuthService {
  ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(AuthLoginDto authLoginDto);

  ResponseEntity<ResponseDto<AuthUserLoginResponseDto>> loginUser(AuthUserLoginDto authUserLoginDto);

  ResponseEntity<ResponseDto<AuthRegisterResponseDto>> register(AuthRegisterDto authLoginDto);

  ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(AuthRefreshTokenDto authRefreshTokenDto);

  ResponseEntity<ResponseDto<AccountResponseDto>> me();

  ResponseEntity<ResponseDto<Boolean>> verifyToken();
}
