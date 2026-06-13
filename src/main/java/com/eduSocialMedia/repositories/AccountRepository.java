package com.eduSocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eduSocialMedia.repositories.customs.AccountRepositoryCustom;
import com.eduSocialMedia.repositories.entities.AccountEntity;


public interface AccountRepository extends JpaRepository<AccountEntity, Long>, JpaSpecificationExecutor<AccountEntity>, AccountRepositoryCustom {
  Optional<AccountEntity> findByUserName(String userName);

  Optional<AccountEntity> findByIdAndIsDeletedFalse(Long id);
}
