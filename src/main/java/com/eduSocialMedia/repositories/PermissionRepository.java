package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.PermissionRepositoryCustom;
import com.eduSocialMedia.repositories.entities.PermissionEntity;

public interface PermissionRepository
    extends JpaRepository<PermissionEntity, Long>, JpaSpecificationExecutor<PermissionEntity>, PermissionRepositoryCustom {
  Optional<PermissionEntity> findByName(String name);

  Optional<PermissionEntity> findByCode(String code);
}