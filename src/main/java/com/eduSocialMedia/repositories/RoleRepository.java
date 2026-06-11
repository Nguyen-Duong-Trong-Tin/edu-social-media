package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.RoleRepositoryCustom;
import com.eduSocialMedia.repositories.entities.RoleEntity;

public interface RoleRepository
    extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity>, RoleRepositoryCustom {
  Optional<RoleEntity> findByName(String name);
}
