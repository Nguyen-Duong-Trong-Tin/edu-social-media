package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;
import com.eduSocialMedia.dtos.groups.GroupFindDto;
import com.eduSocialMedia.repositories.entities.GroupEntity;

public interface GroupRepositoryCustom {
  Specification<GroupEntity> hasCriteria(GroupFindDto query);
}
