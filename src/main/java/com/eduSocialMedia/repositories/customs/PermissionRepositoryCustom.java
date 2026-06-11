package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.eduSocialMedia.dtos.permissions.PermissionFindDto;
import com.eduSocialMedia.repositories.entities.PermissionEntity;

public interface PermissionRepositoryCustom {
  Specification<PermissionEntity> hasCriteria(PermissionFindDto query);
}