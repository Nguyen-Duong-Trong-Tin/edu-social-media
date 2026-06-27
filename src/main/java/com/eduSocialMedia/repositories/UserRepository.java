package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eduSocialMedia.repositories.customs.UserRepositoryCustom;
import com.eduSocialMedia.repositories.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>, UserRepositoryCustom {
  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findBySlug(String slug);

  Optional<UserEntity> findByIdAndIsDeletedFalse(Long id);
}
