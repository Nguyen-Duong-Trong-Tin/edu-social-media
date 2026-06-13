package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;

import com.eduSocialMedia.dtos.accounts.AccountFindDto;
import com.eduSocialMedia.repositories.entities.AccountEntity;

public interface AccountRepositoryCustom {
  Specification<AccountEntity> hasCriteria(AccountFindDto query);
}
