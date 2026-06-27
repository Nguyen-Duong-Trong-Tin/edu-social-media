package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.groupTopics.GroupTopicFindDto;
import com.eduSocialMedia.repositories.customs.GroupTopicRepositoryCustom;
import com.eduSocialMedia.repositories.entities.GroupTopicEntity;
import com.eduSocialMedia.utils.QueryUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class GroupTopicRepositoryCustomImpl implements GroupTopicRepositoryCustom {
  @Override
  public Specification<GroupTopicEntity> hasCriteria(GroupTopicFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Note: GroupTopicEntity does not have an isDeleted or isActive column
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "name", query.getName());

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
