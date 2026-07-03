package com.eduSocialMedia.services.impls;

import com.eduSocialMedia.converters.AccountConverter;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.accounts.AccountResponseDto;
import com.eduSocialMedia.dtos.auth.AuthLoginDto;
import com.eduSocialMedia.dtos.auth.AuthLoginResponseDto;
import com.eduSocialMedia.dtos.auth.AuthRefreshTokenDto;
import com.eduSocialMedia.dtos.auth.AuthRefreshTokenResponseDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.repositories.AccountRepository;
import com.eduSocialMedia.repositories.entities.AccountEntity;
import com.eduSocialMedia.services.AuthService;
import com.eduSocialMedia.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {
  private final HttpServletRequest request;
  private final AccountConverter accountConverter;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AccountRepository accountRepository;

  AuthServiceImpl(HttpServletRequest request, AccountConverter accountConverter, PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil, AccountRepository accountRepository) {
    this.request = request;
    this.accountConverter = accountConverter;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.accountRepository = accountRepository;
  }

  @Override
  public ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(AuthLoginDto authLoginDto) {
    AccountEntity accountEntity = this.accountRepository.findByUserName(authLoginDto.getUserName()).orElse(null);
    if (accountEntity == null) {
      return ResponseDto.badRequest(List.of("User name or password is incorrect"));
    }

    boolean isMatch = passwordEncoder.matches(authLoginDto.getPassword(), accountEntity.getPassword());
    if (!isMatch) {
      return ResponseDto.badRequest(List.of("User name or password is incorrect"));
    }

    String accessToken = this.jwtUtil.generateTokenAC(accountEntity.getUserName(), AuthTypeEnum.ACCOUNT.name());
    String refreshToken = this.jwtUtil.generateTokenRF(accountEntity.getUserName(), AuthTypeEnum.ACCOUNT.name());

    AuthLoginResponseDto authLoginResponseDto = AuthLoginResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
    authLoginResponseDto.setAccount(this.accountConverter.toAccountResponseDto(accountEntity));
    return ResponseDto.success(authLoginResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<AuthRefreshTokenResponseDto>> refreshToken(
      AuthRefreshTokenDto authRefreshTokenDto) {
    String accessToken = authRefreshTokenDto.getAccessToken();
    String refreshToken = authRefreshTokenDto.getRefreshToken();

    Map<String, Object> validationStatusAC = jwtUtil.validateAndExtractAC(accessToken);
    if (validationStatusAC.get("status") == "invalid") {
      return ResponseDto.unauthorized();
    }

    Map<String, Object> validationStatusRF = jwtUtil.validateAndExtractRF(refreshToken);
    if (validationStatusRF.get("status") != "success") {
      return ResponseDto.unauthorized();
    }

    String email = (String) validationStatusRF.get("email");
    String role = (String) validationStatusRF.get("role");

    String newAccessToken = this.jwtUtil.generateTokenAC(email, role);
    return ResponseDto.success(AuthRefreshTokenResponseDto.builder()
        .accessToken(newAccessToken)
        .build());
  }

  @Override
  public ResponseEntity<ResponseDto<AccountResponseDto>> me() {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseDto.unauthorized();
    }
    String accessToken = authHeader.substring(7);

    Map<String, Object> validationStatusAC = jwtUtil.validateAndExtractAC(accessToken);
    if (!"success".equals(validationStatusAC.get("status"))) {
      return ResponseDto.unauthorized();
    }

    @SuppressWarnings("unchecked")
    Map<String, String> userData = (Map<String, String>) validationStatusAC.get("data");
    String userName = userData.get("email");

    AccountEntity accountEntity = this.accountRepository.findByUserName(userName).orElse(null);
    if (accountEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    return ResponseDto.success(this.accountConverter.toAccountResponseDto(accountEntity));
  }

  @Override
  public ResponseEntity<ResponseDto<Boolean>> verifyToken() {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseDto.unauthorized();
    }
    String accessToken = authHeader.substring(7);

    Map<String, Object> validationStatusAC = jwtUtil.validateAndExtractAC(accessToken);
    if (!"success".equals(validationStatusAC.get("status"))) {
      return ResponseDto.unauthorized();
    }

    return ResponseDto.success(true);
  }
}
