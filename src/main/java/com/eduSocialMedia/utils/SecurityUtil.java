package com.eduSocialMedia.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.auth.AuthPrincipal;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.UserEntity;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class SecurityUtil {
  private final HttpServletRequest request;
  private final UserRepository userRepository;

  public SecurityUtil(HttpServletRequest request, UserRepository userRepository) {
    this.request = request;
    this.userRepository = userRepository;
  }

  /**
   * Checks if a USER caller owns the target user id (ACCOUNT callers are always allowed).
   * Uses the AuthPrincipal set by AuthInterceptor, so the endpoint must be annotated with @Auth.
   * If not allowed, returns the appropriate error ResponseEntity (unauthorized or forbidden).
   * Otherwise, returns null.
   */
  public <T> ResponseEntity<ResponseDto<T>> validateUserOwnership(Long targetUserId) {
    AuthPrincipal principal = (AuthPrincipal) request.getAttribute(AuthPrincipal.ATTRIBUTE);
    if (principal == null) {
      return ResponseDto.unauthorized();
    }

    // If it's a USER role, check if they own the resource
    if (principal.role() == AuthTypeEnum.USER) {
      UserEntity loggedInUser = this.userRepository.findByEmail(principal.subject()).orElse(null);
      if (loggedInUser == null || !loggedInUser.getId().equals(targetUserId)) {
        return ResponseDto.forbidden();
      }
    }

    return null;
  }
}
