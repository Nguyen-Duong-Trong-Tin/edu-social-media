package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionFindDto;
import com.eduSocialMedia.repositories.entities.GroupTaskSubmissionEntity;

public interface GroupTaskSubmissionRepositoryCustom {
  Specification<GroupTaskSubmissionEntity> hasCriteria(GroupTaskSubmissionFindDto query);
}
