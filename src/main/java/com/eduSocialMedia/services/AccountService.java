package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.accounts.AccountCreateDto;
import com.eduSocialMedia.dtos.accounts.AccountFindDto;
import com.eduSocialMedia.dtos.accounts.AccountResponseDto;
import com.eduSocialMedia.dtos.accounts.AccountUpdateDto;

public interface AccountService {
  ResponseEntity<ResponseDto<AccountResponseDto>> create(AccountCreateDto accountCreateDto);

  ResponseEntity<ResponseDto<AccountResponseDto>> update(Long id, AccountUpdateDto accountUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<AccountResponseDto>>> find(AccountFindDto query,
      Pageable pageable);

  ResponseEntity<ResponseDto<AccountResponseDto>> findById(Long id);
}
