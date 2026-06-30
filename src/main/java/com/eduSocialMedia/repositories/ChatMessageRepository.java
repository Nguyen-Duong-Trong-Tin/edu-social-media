package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.repositories.customs.ChatMessageRepositoryCustom;
import com.eduSocialMedia.repositories.entities.ChatMessageEntity;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long>, JpaSpecificationExecutor<ChatMessageEntity>, ChatMessageRepositoryCustom {
  Optional<ChatMessageEntity> findByIdAndIsDeletedFalse(Long id);
}
