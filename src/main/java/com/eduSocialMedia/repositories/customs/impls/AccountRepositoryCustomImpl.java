package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.accounts.AccountFindDto;
import com.eduSocialMedia.repositories.customs.AccountRepositoryCustom;
import com.eduSocialMedia.repositories.entities.AccountEntity;
import com.eduSocialMedia.utils.QueryUtil;
import com.eduSocialMedia.utils.ValidationUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {
  public Specification<AccountEntity> hasCriteria(AccountFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isDeleted", false);
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "fullName", query.getFullName());
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "userName", query.getUserName());
      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isActive", query.getIsActive());  

      if (query.getRoleId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("role").get("id"), query.getRoleId()));
      }

      if (!ValidationUtil.isNullOrEmpty(query.getRoleName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("role").get("name")),
            "%" + query.getRoleName().toLowerCase() + "%"));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
