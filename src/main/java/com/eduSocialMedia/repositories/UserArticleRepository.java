package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.UserArticleRepositoryCustom;
import com.eduSocialMedia.repositories.entities.UserArticleEntity;

public interface UserArticleRepository extends JpaRepository<UserArticleEntity, Long>, JpaSpecificationExecutor<UserArticleEntity>, UserArticleRepositoryCustom {
  Optional<UserArticleEntity> findBySlug(String slug);

  Optional<UserArticleEntity> findByNameAndIsDeletedFalse(String name);

  Optional<UserArticleEntity> findByIdAndIsDeletedFalse(Long id);
}
