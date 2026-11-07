package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;
import com.eduSocialMedia.dtos.userArticles.UserArticleFindDto;
import com.eduSocialMedia.repositories.entities.UserArticleEntity;

public interface UserArticleRepositoryCustom {
  Specification<UserArticleEntity> hasCriteria(UserArticleFindDto query);

  /**
   * PUBLIC articles, plus every article owned by userId.
   */
  Specification<UserArticleEntity> isVisibleTo(Long userId);
}
