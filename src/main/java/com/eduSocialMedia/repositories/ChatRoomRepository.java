package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.ChatRoomRepositoryCustom;
import com.eduSocialMedia.repositories.entities.ChatRoomEntity;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>, JpaSpecificationExecutor<ChatRoomEntity>, ChatRoomRepositoryCustom {
  Optional<ChatRoomEntity> findBySlug(String slug);

  Optional<ChatRoomEntity> findByNameAndIsDeletedFalse(String name);

  Optional<ChatRoomEntity> findByIdAndIsDeletedFalse(Long id);
}
