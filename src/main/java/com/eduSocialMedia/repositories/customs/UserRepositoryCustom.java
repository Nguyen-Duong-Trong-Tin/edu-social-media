package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.eduSocialMedia.dtos.users.UserFindDto;
import com.eduSocialMedia.repositories.entities.UserEntity;

public interface UserRepositoryCustom {
  Specification<UserEntity> hasCriteria(UserFindDto query);
}
