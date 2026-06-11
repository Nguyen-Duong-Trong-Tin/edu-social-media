package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.eduSocialMedia.dtos.roles.RoleFindDto;
import com.eduSocialMedia.repositories.entities.RoleEntity;

public interface RoleRepositoryCustom {
  Specification<RoleEntity> hasCriteria(RoleFindDto query);
}
