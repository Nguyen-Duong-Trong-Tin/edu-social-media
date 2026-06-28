package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleFindDto;
import com.eduSocialMedia.repositories.entities.GroupArticleEntity;

public interface GroupArticleRepositoryCustom {
  Specification<GroupArticleEntity> hasCriteria(GroupArticleFindDto query);
}
