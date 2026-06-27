package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.eduSocialMedia.dtos.groupTopics.GroupTopicFindDto;
import com.eduSocialMedia.repositories.entities.GroupTopicEntity;

public interface GroupTopicRepositoryCustom {
  Specification<GroupTopicEntity> hasCriteria(GroupTopicFindDto query);
}
