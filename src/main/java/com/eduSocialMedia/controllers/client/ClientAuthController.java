package com.eduSocialMedia.controllers.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduSocialMedia.annotations.Auth;
import com.eduSocialMedia.annotations.PublicApi;
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
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.services.client.ClientAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class ClientAuthController {
  private final ClientAuthService authService;

  ClientAuthController(ClientAuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  @PublicApi
  public ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(
      @Valid @RequestBody AuthLoginDto authLoginDto) {
    return this.authService.login(authLoginDto);
  }

  @PostMapping("/user/login")
  @PublicApi
  public ResponseEntity<ResponseDto<AuthUserLoginResponseDto>> loginUser(
      @Valid @RequestBody AuthUserLoginDto authLoginDto) {
    return this.authService.loginUser(authLoginDto);
  }

  @PostMapping("/register")
  @PublicApi
  public ResponseEntity<ResponseDto<AuthRegisterResponseDto>> register(
      @Valid @RequestBody AuthRegisterDto authRegisterDto) {
    return this.authService.register(authRegisterDto);
  }

  @PostMapping("/refresh-token")
  @PublicApi
  public ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(
      @RequestBody @Valid AuthRefreshTokenDto authRefreshTokenDto) {
    return this.authService.refreshToken(authRefreshTokenDto);
  }

  @PostMapping("/me")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<AccountResponseDto>> me() {
    return this.authService.me();
  }

  @PostMapping("/user/me")
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<com.eduSocialMedia.dtos.users.UserResponseDto>> userMe() {
    return this.authService.userMe();
  }

  @PostMapping("/verify-token")
  @Auth({ AuthTypeEnum.ACCOUNT, AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<Boolean>> verifyToken() {
    return this.authService.verifyToken();
  }

  @PostMapping("/logout")
  @Auth({ AuthTypeEnum.ACCOUNT, AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<Object>> logout() {
    return this.authService.logout();
  }
}
