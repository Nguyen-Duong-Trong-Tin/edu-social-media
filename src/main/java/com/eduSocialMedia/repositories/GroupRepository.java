package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.GroupRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Long>, JpaSpecificationExecutor<GroupEntity>, GroupRepositoryCustom {
  Optional<GroupEntity> findBySlug(String slug);

  Optional<GroupEntity> findByNameAndIsDeletedFalse(String name);

  Optional<GroupEntity> findByIdAndIsDeletedFalse(Long id);
}
