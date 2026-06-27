package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.repositories.customs.GroupTopicRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupTopicEntity;

@Repository
public interface GroupTopicRepository extends JpaRepository<GroupTopicEntity, Long>, JpaSpecificationExecutor<GroupTopicEntity>, GroupTopicRepositoryCustom {
  Optional<GroupTopicEntity> findBySlug(String slug);

  Optional<GroupTopicEntity> findByName(String name);
}
