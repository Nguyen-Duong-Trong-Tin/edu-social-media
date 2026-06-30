package com.eduSocialMedia.repositories.customs.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.dtos.chatMessages.ChatMessageFindDto;
import com.eduSocialMedia.repositories.customs.ChatMessageRepositoryCustom;
import com.eduSocialMedia.repositories.entities.ChatMessageEntity;
import com.eduSocialMedia.utils.QueryUtil;
import com.eduSocialMedia.utils.ValidationUtil;

import jakarta.persistence.criteria.Predicate;

@Repository
public class ChatMessageRepositoryCustomImpl implements ChatMessageRepositoryCustom {
  @Override
  public Specification<ChatMessageEntity> hasCriteria(ChatMessageFindDto query) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      QueryUtil.addEqualPredicate(root, criteriaBuilder, predicates, "isDeleted", false);
      QueryUtil.addLikePredicate(root, criteriaBuilder, predicates, "content", query.getContent());

      if (query.getUserId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("user").get("id"), query.getUserId()));
      }

      if (!ValidationUtil.isNullOrEmpty(query.getUserName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("user").get("fullName")),
            "%" + query.getUserName().toLowerCase() + "%"));
      }

      if (query.getChatRoomId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("chatRoom").get("id"), query.getChatRoomId()));
      }

      if (!ValidationUtil.isNullOrEmpty(query.getChatRoomName())) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.join("chatRoom").get("name")),
            "%" + query.getChatRoomName().toLowerCase() + "%"));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
