package com.eduSocialMedia.services.impls;

import com.eduSocialMedia.converters.AccountConverter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.auth.AuthLoginDto;
import com.eduSocialMedia.dtos.auth.AuthLoginResponseDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.repositories.AccountRepository;
import com.eduSocialMedia.repositories.entities.AccountEntity;
import com.eduSocialMedia.services.AuthService;
import com.eduSocialMedia.utils.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
  private final AccountConverter accountConverter;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AccountRepository accountRepository;

  AuthServiceImpl(AccountConverter accountConverter) {
    this.accountConverter = accountConverter;
  }

  @Override
  public ResponseEntity<ResponseDto<AuthLoginResponseDto>> login(AuthLoginDto authLoginDto) {
    AccountEntity accountEntity = this.accountRepository.findByUserName(authLoginDto.getUserName()).orElse(null);
    if (accountEntity == null) {
      return ResponseDto.badRequest(List.of("User name or password is incorrect"));
    }

    boolean isMatch = passwordEncoder.matches(authLoginDto.getPassword(), accountEntity.getPassword());
    if (!isMatch) {
      return ResponseDto.notFound("User name or password is incorrect");
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
}
