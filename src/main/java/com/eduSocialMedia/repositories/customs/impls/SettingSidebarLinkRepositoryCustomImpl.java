package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkFindDto;
import com.eduSocialMedia.repositories.customs.SettingSidebarLinkRepositoryCustom;
import com.eduSocialMedia.repositories.entities.SettingSidebarLinkEntity;
import com.eduSocialMedia.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class SettingSidebarLinkRepositoryCustomImpl implements SettingSidebarLinkRepositoryCustom {
  @Override
  public Specification<SettingSidebarLinkEntity> hasCriteria(SettingSidebarLinkFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (query != null) {
        QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());
        QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "path", query.getPath());
        QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "icon", query.getIcon());
        QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isExact", query.getIsExact());
        QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "sortOrder", query.getSortOrder());
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
