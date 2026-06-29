package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;
import com.eduSocialMedia.dtos.chatRooms.ChatRoomFindDto;
import com.eduSocialMedia.repositories.entities.ChatRoomEntity;

public interface ChatRoomRepositoryCustom {
  Specification<ChatRoomEntity> hasCriteria(ChatRoomFindDto query);
}
