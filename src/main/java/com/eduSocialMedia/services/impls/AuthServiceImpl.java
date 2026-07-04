package com.eduSocialMedia.services.impls;

import com.eduSocialMedia.converters.AccountConverter;
import com.eduSocialMedia.converters.UserConverter;

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
import com.eduSocialMedia.dtos.auth.AuthRegisterDto;
import com.eduSocialMedia.dtos.auth.AuthRegisterResponseDto;
import com.eduSocialMedia.dtos.auth.AuthUserLoginDto;
import com.eduSocialMedia.dtos.auth.AuthUserLoginResponseDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.repositories.AccountRepository;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.AccountEntity;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.AuthService;
import com.eduSocialMedia.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {
  private final HttpServletRequest request;
  private final AccountConverter accountConverter;
  private final UserConverter userConverter;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AccountRepository accountRepository;
  private final UserRepository userRepository;

  AuthServiceImpl(HttpServletRequest request, AccountConverter accountConverter, UserConverter userConverter,
      PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil, AccountRepository accountRepository, UserRepository userRepository) {
    this.request = request;
    this.accountConverter = accountConverter;
    this.userConverter = userConverter;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
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
  public ResponseEntity<ResponseDto<AuthUserLoginResponseDto>> loginUser(AuthUserLoginDto authUserLoginDto) {
    UserEntity userEntity = this.userRepository.findByEmail(authUserLoginDto.getEmail()).orElse(null);
    if (userEntity == null) {
      return ResponseDto.badRequest(List.of("Email or password is incorrect"));
    }

    boolean isMatch = passwordEncoder.matches(authUserLoginDto.getPassword(), userEntity.getPassword());
    if (!isMatch) {
      return ResponseDto.badRequest(List.of("Email or password is incorrect"));
    }

    String accessToken = this.jwtUtil.generateTokenAC(userEntity.getEmail(), AuthTypeEnum.USER.name());
    String refreshToken = this.jwtUtil.generateTokenRF(userEntity.getEmail(), AuthTypeEnum.USER.name());

    AuthUserLoginResponseDto authUserLoginResponseDto = AuthUserLoginResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
    authUserLoginResponseDto.setUser(this.userConverter.toUserResponseDto(userEntity));
    return ResponseDto.success(authUserLoginResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<AuthRegisterResponseDto>> register(AuthRegisterDto authRegisterDto) {
    UserEntity userExists = this.userRepository.findByEmail(authRegisterDto.getEmail()).orElse(null);
    if (userExists != null) {
      return ResponseDto.badRequest(List.of("Email already exists"));
    }

    UserEntity userEntity = this.userConverter.toUserEntity(authRegisterDto);
    userEntity.setPassword(this.passwordEncoder.encode(authRegisterDto.getPassword()));
    userEntity.setSlug(generateUniqueSlug(authRegisterDto.getFullName()));
    userEntity.setIsActive(true);
    userEntity.setIsDeleted(false);

    userEntity = this.userRepository.save(userEntity);

    AuthRegisterResponseDto authRegisterResponseDto = this.userConverter.toAuthRegisterResponseDto(userEntity);
    return ResponseDto.created(authRegisterResponseDto);
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

  private String generateSlug(String fullName) {
    if (fullName == null) {
      return "";
    }
    String temp = fullName.toLowerCase().trim()
        .replaceAll("[^a-z0-9\\s-]", "")
        .replaceAll("[\\s-]+", "-");
    if (temp.startsWith("-")) {
      temp = temp.substring(1);
    }
    if (temp.endsWith("-")) {
      temp = temp.substring(0, temp.length() - 1);
    }
    return temp;
  }

  private String generateUniqueSlug(String fullName) {
    String baseSlug = generateSlug(fullName);
    String slug = baseSlug;
    int counter = 1;
    while (this.userRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }
}
