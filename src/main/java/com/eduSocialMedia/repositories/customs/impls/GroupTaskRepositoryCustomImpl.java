package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.groupTasks.GroupTaskFindDto;
import com.eduSocialMedia.repositories.customs.GroupTaskRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupTaskEntity;
import com.eduSocialMedia.utils.QueryUtil;
import com.eduSocialMedia.utils.ValidationUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class GroupTaskRepositoryCustomImpl implements GroupTaskRepositoryCustom {
  @Override
  public Specification<GroupTaskEntity> hasCriteria(GroupTaskFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isDeleted", false);
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());
      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isActive", query.getIsActive());

      if (query.getGroupId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("group").get("id"), query.getGroupId()));
      }

      if (!ValidationUtil.isNullOrEmpty(query.getGroupName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("group").get("name")),
            "%" + query.getGroupName().toLowerCase() + "%"));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
