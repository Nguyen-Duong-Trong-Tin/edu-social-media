package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;
import com.eduSocialMedia.dtos.chatMessages.ChatMessageFindDto;
import com.eduSocialMedia.repositories.entities.ChatMessageEntity;

public interface ChatMessageRepositoryCustom {
  Specification<ChatMessageEntity> hasCriteria(ChatMessageFindDto query);
}
