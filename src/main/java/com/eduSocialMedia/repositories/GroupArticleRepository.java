package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.repositories.customs.GroupArticleRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupArticleEntity;

@Repository
public interface GroupArticleRepository extends JpaRepository<GroupArticleEntity, Long>, JpaSpecificationExecutor<GroupArticleEntity>, GroupArticleRepositoryCustom {
  Optional<GroupArticleEntity> findBySlug(String slug);

  Optional<GroupArticleEntity> findByNameAndIsDeletedFalse(String name);

  Optional<GroupArticleEntity> findByIdAndIsDeletedFalse(Long id);
}
