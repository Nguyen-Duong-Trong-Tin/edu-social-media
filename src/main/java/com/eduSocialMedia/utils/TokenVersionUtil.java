package com.eduSocialMedia.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.repositories.AccountRepository;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.AccountEntity;
import com.eduSocialMedia.repositories.entities.UserEntity;

/**
 * Tracks token_version of users/accounts so issued JWTs can be revoked.
 * A token is only accepted while its "ver" claim equals the current token_version
 * of an active, non-deleted user/account. Lookups are cached for one minute.
 */
@Component
public class TokenVersionUtil {
  private static final long CACHE_TTL_MS = 60_000;

  private final UserRepository userRepository;
  private final AccountRepository accountRepository;
  private final Map<String, CachedVersion> cache = new ConcurrentHashMap<>();

  private record CachedVersion(Integer version, long expiresAt) {
  }

  public TokenVersionUtil(UserRepository userRepository, AccountRepository accountRepository) {
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
  }

  public boolean isCurrent(String subject, String role, String version) {
    if (subject == null || role == null || version == null) {
      return false;
    }
    Integer currentVersion = getCurrentVersion(subject, role);
    return currentVersion != null && currentVersion.toString().equals(version);
  }

  /**
   * Increments token_version and saves the user, invalidating all of its issued tokens.
   */
  public void revokeTokens(UserEntity userEntity) {
    userEntity.setTokenVersion(userEntity.getTokenVersion() + 1);
    this.userRepository.save(userEntity);
    this.cache.remove(cacheKey(userEntity.getEmail(), AuthTypeEnum.USER.name()));
  }

  /**
   * Increments token_version and saves the account, invalidating all of its issued tokens.
   */
  public void revokeTokens(AccountEntity accountEntity) {
    accountEntity.setTokenVersion(accountEntity.getTokenVersion() + 1);
    this.accountRepository.save(accountEntity);
    this.cache.remove(cacheKey(accountEntity.getUserName(), AuthTypeEnum.ACCOUNT.name()));
  }

  public void revokeTokens(String subject, String role) {
    if (AuthTypeEnum.USER.name().equals(role)) {
      this.userRepository.findByEmail(subject).ifPresent(this::revokeTokens);
    } else if (AuthTypeEnum.ACCOUNT.name().equals(role)) {
      this.accountRepository.findByUserName(subject).ifPresent(this::revokeTokens);
    }
  }

  private Integer getCurrentVersion(String subject, String role) {
    String key = cacheKey(subject, role);
    CachedVersion cached = this.cache.get(key);
    if (cached != null && cached.expiresAt() > System.currentTimeMillis()) {
      return cached.version();
    }

    Integer version = loadCurrentVersion(subject, role);
    this.cache.put(key, new CachedVersion(version, System.currentTimeMillis() + CACHE_TTL_MS));
    return version;
  }

  // Returns null when the user/account is missing, deleted or inactive
  private Integer loadCurrentVersion(String subject, String role) {
    if (AuthTypeEnum.USER.name().equals(role)) {
      return this.userRepository.findByEmailAndIsDeletedFalseAndIsActiveTrue(subject)
          .map(UserEntity::getTokenVersion).orElse(null);
    }
    if (AuthTypeEnum.ACCOUNT.name().equals(role)) {
      return this.accountRepository.findByUserNameAndIsDeletedFalseAndIsActiveTrue(subject)
          .map(AccountEntity::getTokenVersion).orElse(null);
    }
    return null;
  }

  private String cacheKey(String subject, String role) {
    return role + ":" + subject;
  }
}
