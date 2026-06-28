package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskFindDto;
import com.eduSocialMedia.repositories.entities.GroupTaskEntity;

public interface GroupTaskRepositoryCustom {
  Specification<GroupTaskEntity> hasCriteria(GroupTaskFindDto query);
}
