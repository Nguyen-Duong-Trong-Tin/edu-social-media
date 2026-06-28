package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.GroupTaskSubmissionRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupTaskSubmissionEntity;

public interface GroupTaskSubmissionRepository extends JpaRepository<GroupTaskSubmissionEntity, Long>, JpaSpecificationExecutor<GroupTaskSubmissionEntity>, GroupTaskSubmissionRepositoryCustom {
  Optional<GroupTaskSubmissionEntity> findBySlug(String slug);

  Optional<GroupTaskSubmissionEntity> findByNameAndIsDeletedFalse(String name);

  Optional<GroupTaskSubmissionEntity> findByIdAndIsDeletedFalse(Long id);
}
