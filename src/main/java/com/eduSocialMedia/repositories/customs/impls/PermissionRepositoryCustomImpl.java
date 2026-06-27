package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.permissions.PermissionFindDto;
import com.eduSocialMedia.repositories.customs.PermissionRepositoryCustom;
import com.eduSocialMedia.repositories.entities.PermissionEntity;
import com.eduSocialMedia.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class PermissionRepositoryCustomImpl implements PermissionRepositoryCustom {
  public Specification<PermissionEntity> hasCriteria(PermissionFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "code", query.getCode());
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "category", query.getCategory());

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}