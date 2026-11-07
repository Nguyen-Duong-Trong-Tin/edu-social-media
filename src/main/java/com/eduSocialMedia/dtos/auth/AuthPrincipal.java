package com.eduSocialMedia.dtos.auth;

import com.eduSocialMedia.enums.AuthTypeEnum;

/**
 * Caller authenticated by AuthInterceptor, stored as a request attribute.
 * subject is the email for USER tokens and the user name for ACCOUNT tokens.
 */
public record AuthPrincipal(String subject, AuthTypeEnum role) {
  public static final String ATTRIBUTE = AuthPrincipal.class.getName();
}
