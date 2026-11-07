package com.eduSocialMedia.annotations;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.eduSocialMedia.dtos.auth.AuthPrincipal;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.exceptions.ForbiddenException;
import com.eduSocialMedia.exceptions.UnauthorizedException;
import com.eduSocialMedia.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Authenticates /v1/** requests before argument resolution, so @CurrentUser can use the result.
 * Endpoints must opt in with @Auth or @PublicApi; a forgotten annotation is rejected instead of being public.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
  private final JwtUtil jwtUtil;

  AuthInterceptor(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (!(handler instanceof HandlerMethod method) || method.hasMethodAnnotation(PublicApi.class)) {
      return true;
    }

    Auth auth = method.getMethodAnnotation(Auth.class);
    if (auth == null) {
      throw new ForbiddenException();
    }

    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException();
    }

    Map<String, Object> validation = this.jwtUtil.validateAndExtractAC(authHeader.substring(7));
    if (!"success".equals(validation.get("status"))) {
      throw new UnauthorizedException();
    }

    @SuppressWarnings("unchecked")
    Map<String, String> data = (Map<String, String>) validation.get("data");
    AuthTypeEnum role = AuthTypeEnum.valueOf(data.get("role"));
    AuthTypeEnum[] requiredRoles = auth.value();
    if (requiredRoles.length > 0 && !Arrays.asList(requiredRoles).contains(role)) {
      throw new ForbiddenException();
    }

    request.setAttribute(AuthPrincipal.ATTRIBUTE, new AuthPrincipal(data.get("email"), role));
    return true;
  }
}
