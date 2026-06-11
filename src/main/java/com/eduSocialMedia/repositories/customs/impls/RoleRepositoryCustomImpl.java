package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.roles.RoleFindDto;
import com.eduSocialMedia.repositories.customs.RoleRepositoryCustom;
import com.eduSocialMedia.repositories.entities.RoleEntity;
import com.eduSocialMedia.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {
  public Specification<RoleEntity> hasCriteria(RoleFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());

      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "description", query.getDescription());

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}