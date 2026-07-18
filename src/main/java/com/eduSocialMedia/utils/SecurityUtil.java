package com.eduSocialMedia.utils;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.UserEntity;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class SecurityUtil {
  private final HttpServletRequest request;
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  public SecurityUtil(HttpServletRequest request, JwtUtil jwtUtil, UserRepository userRepository) {
    this.request = request;
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
  }

  /**
   * Validates request token and check if USER has ownership of the target resource id.
   * If not allowed, returns the appropriate error ResponseEntity (unauthorized or forbidden).
   * Otherwise, returns null.
   */
  public <T> ResponseEntity<ResponseDto<T>> validateUserOwnership(Long targetUserId) {
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
    String role = userData.get("role");
    String email = userData.get("email");

    // If it's a USER role, check if they own the resource
    if (AuthTypeEnum.USER.name().equalsIgnoreCase(role)) {
      UserEntity loggedInUser = this.userRepository.findByEmail(email).orElse(null);
      if (loggedInUser == null || !loggedInUser.getId().equals(targetUserId)) {
        return ResponseDto.forbidden();
      }
    }

    return null;
  }
}
