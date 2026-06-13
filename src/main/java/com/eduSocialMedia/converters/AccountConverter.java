package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.accounts.AccountCreateDto;
import com.eduSocialMedia.dtos.accounts.AccountResponseDto;
import com.eduSocialMedia.dtos.accounts.AccountUpdateDto;
import com.eduSocialMedia.repositories.entities.AccountEntity;

@Component
public class AccountConverter {
  @Autowired
  private ModelMapper modelMapper;

  public AccountEntity toAccountEntity(AccountCreateDto accountCreateDto) {
    return this.modelMapper.map(accountCreateDto, AccountEntity.class);
  }

  public AccountResponseDto toAccountResponseDto(AccountEntity accountEntity) {
    return this.modelMapper.map(accountEntity, AccountResponseDto.class);
  }

  public AccountEntity copyToAccountEntity(AccountUpdateDto accountUpdateDto,
      AccountEntity accountEntity) {
    this.modelMapper.map(accountUpdateDto, accountEntity);
    return accountEntity;
  }
}
