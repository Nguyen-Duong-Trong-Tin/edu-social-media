package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.SettingSidebarLinkRepositoryCustom;
import com.eduSocialMedia.repositories.entities.SettingSidebarLinkEntity;

public interface SettingSidebarLinkRepository
    extends JpaRepository<SettingSidebarLinkEntity, Long>,
            JpaSpecificationExecutor<SettingSidebarLinkEntity>,
            SettingSidebarLinkRepositoryCustom {
  Optional<SettingSidebarLinkEntity> findByPath(String path);
  boolean existsByPath(String path);
}
