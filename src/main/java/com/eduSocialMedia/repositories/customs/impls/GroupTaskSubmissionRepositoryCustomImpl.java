package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionFindDto;
import com.eduSocialMedia.repositories.customs.GroupTaskSubmissionRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupTaskSubmissionEntity;
import com.eduSocialMedia.utils.QueryUtil;
import com.eduSocialMedia.utils.ValidationUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class GroupTaskSubmissionRepositoryCustomImpl implements GroupTaskSubmissionRepositoryCustom {
  @Override
  public Specification<GroupTaskSubmissionEntity> hasCriteria(GroupTaskSubmissionFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isDeleted", false);
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());
      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isActive", query.getIsActive());

      if (query.getGroupTaskId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("groupTask").get("id"), query.getGroupTaskId()));
      }

      if (!ValidationUtil.isNullOrEmpty(query.getGroupTaskName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("groupTask").get("name")),
            "%" + query.getGroupTaskName().toLowerCase() + "%"));
      }

      if (query.getUserId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("user").get("id"), query.getUserId()));
      }

      if (!ValidationUtil.isNullOrEmpty(query.getUserName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("user").get("fullName")),
            "%" + query.getUserName().toLowerCase() + "%"));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
