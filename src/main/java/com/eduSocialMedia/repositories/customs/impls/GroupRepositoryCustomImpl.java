package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.groups.GroupFindDto;
import com.eduSocialMedia.repositories.customs.GroupRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupEntity;
import com.eduSocialMedia.utils.QueryUtil;
import com.eduSocialMedia.utils.ValidationUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class GroupRepositoryCustomImpl implements GroupRepositoryCustom {
  @Override
  public Specification<GroupEntity> hasCriteria(GroupFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isDeleted", false);
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());
      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isActive", query.getIsActive());

      if (query.getGroupTopicId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("groupTopic").get("id"), query.getGroupTopicId()));
      }

      if (!ValidationUtil.isNullOrEmpty(query.getGroupTopicName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("groupTopic").get("name")),
            "%" + query.getGroupTopicName().toLowerCase() + "%"));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
