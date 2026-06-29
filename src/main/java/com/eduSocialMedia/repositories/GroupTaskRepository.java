package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.GroupTaskRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupTaskEntity;

public interface GroupTaskRepository extends JpaRepository<GroupTaskEntity, Long>, JpaSpecificationExecutor<GroupTaskEntity>, GroupTaskRepositoryCustom {
  Optional<GroupTaskEntity> findBySlug(String slug);

  Optional<GroupTaskEntity> findByNameAndIsDeletedFalse(String name);

  Optional<GroupTaskEntity> findByIdAndIsDeletedFalse(Long id);
}
