package com.eduSocialMedia.annotations;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.eduSocialMedia.dtos.auth.AuthPrincipal;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.exceptions.ForbiddenException;
import com.eduSocialMedia.exceptions.UnauthorizedException;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.UserEntity;

/**
 * Resolves @CurrentUser parameters from the AuthPrincipal set by AuthInterceptor.
 */
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
  private final UserRepository userRepository;

  CurrentUserArgumentResolver(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentUser.class)
        && UserEntity.class.equals(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    AuthPrincipal principal = (AuthPrincipal) webRequest.getAttribute(AuthPrincipal.ATTRIBUTE,
        RequestAttributes.SCOPE_REQUEST);
    // ACCOUNT tokens have no UserEntity; endpoints using @CurrentUser must be @Auth({ AuthTypeEnum.USER })
    if (principal == null || principal.role() != AuthTypeEnum.USER) {
      throw new ForbiddenException();
    }

    return this.userRepository.findByEmailAndIsDeletedFalseAndIsActiveTrue(principal.subject())
        .orElseThrow(UnauthorizedException::new);
  }
}
