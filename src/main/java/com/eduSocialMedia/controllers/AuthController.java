package com.eduSocialMedia.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.eduSocialMedia.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
  private final AuthService authService;

  AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(
      @Valid @RequestBody AuthLoginDto authLoginDto) {
    return this.authService.login(authLoginDto);
  }

  @PostMapping("/user/login")
  public ResponseEntity<ResponseDto<AuthUserLoginResponseDto>> loginUser(
      @Valid @RequestBody AuthUserLoginDto authLoginDto) {
    return this.authService.loginUser(authLoginDto);
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDto<AuthRegisterResponseDto>> register(
      @Valid @RequestBody AuthRegisterDto authRegisterDto) {
    return this.authService.register(authRegisterDto);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(
      @RequestBody @Valid AuthRefreshTokenDto authRefreshTokenDto) {
    return this.authService.refreshToken(authRefreshTokenDto);
  }

  @PostMapping("/me")
  public ResponseEntity<ResponseDto<AccountResponseDto>> me() {
    return this.authService.me();
  }

  @PostMapping("/user/me")
  public ResponseEntity<ResponseDto<com.eduSocialMedia.dtos.users.UserResponseDto>> userMe() {
    return this.authService.userMe();
  }

  @PostMapping("/verify-token")
  public ResponseEntity<ResponseDto<Boolean>> verifyToken() {
    return this.authService.verifyToken();
  }
}
