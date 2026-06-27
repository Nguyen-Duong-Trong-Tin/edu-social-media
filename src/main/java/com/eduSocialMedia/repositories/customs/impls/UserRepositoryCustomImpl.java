package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.users.UserFindDto;
import com.eduSocialMedia.repositories.customs.UserRepositoryCustom;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
  @Override
  public Specification<UserEntity> hasCriteria(UserFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isDeleted", false);
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "fullName", query.getFullName());
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "email", query.getEmail());
      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isActive", query.getIsActive());

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
