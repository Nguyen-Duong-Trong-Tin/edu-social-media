package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.eduSocialMedia.converters.AccountConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.accounts.AccountCreateDto;
import com.eduSocialMedia.dtos.accounts.AccountFindDto;
import com.eduSocialMedia.dtos.accounts.AccountResponseDto;
import com.eduSocialMedia.dtos.accounts.AccountUpdateDto;
import com.eduSocialMedia.repositories.AccountRepository;
import com.eduSocialMedia.repositories.RoleRepository;
import com.eduSocialMedia.repositories.entities.AccountEntity;
import com.eduSocialMedia.repositories.entities.RoleEntity;
import com.eduSocialMedia.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
  @Autowired
  private AccountConverter accountConverter;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ResponseEntity<ResponseDto<AccountResponseDto>> create(AccountCreateDto accountCreateDto) {
    AccountEntity accountEntity = this.accountConverter.toAccountEntity(accountCreateDto);
    accountEntity.setPassword(this.passwordEncoder.encode(accountCreateDto.getPassword()));

    AccountEntity accountUsernameExists = this.accountRepository.findByUserName(accountCreateDto.getUserName())
        .orElse(null);
    if (accountUsernameExists != null) {
      return ResponseDto.badRequest(List.of("Username already exists"));
    }

    RoleEntity roleEntity = this.roleRepository.findById(accountCreateDto.getRoleId()).orElse(null);
    if (roleEntity == null) {
      return ResponseDto.notFound("Role not found");
    }
    accountEntity.setRole(roleEntity);

    accountEntity = this.accountRepository.save(accountEntity);

    AccountResponseDto accountResponseDto = this.accountConverter.toAccountResponseDto(accountEntity);
    return ResponseDto.created(accountResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<AccountResponseDto>> update(Long id, AccountUpdateDto accountUpdateDto) {
    AccountEntity accountEntity = this.accountRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (accountEntity == null) {
      return ResponseDto.notFound("Account not found");
    }

    if (accountUpdateDto.getRoleId() != null) {
      RoleEntity roleEntity = this.roleRepository.findById(accountUpdateDto.getRoleId()).orElse(null);
      if (roleEntity == null) {
        return ResponseDto.notFound("Role not found");
      }
      accountEntity.setRole(roleEntity);
    }

    this.accountConverter.copyToAccountEntity(accountUpdateDto, accountEntity);

    accountEntity = this.accountRepository.save(accountEntity);

    AccountResponseDto accountResponseDto = this.accountConverter.toAccountResponseDto(accountEntity);
    return ResponseDto.success(accountResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    AccountEntity accountEntity = this.accountRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (accountEntity == null) {
      return ResponseDto.notFound("Account not found");
    }

    accountEntity.setIsDeleted(true);
    this.accountRepository.save(accountEntity);
    
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<AccountResponseDto>>> find(AccountFindDto query,
      Pageable pageable) {
    Specification<AccountEntity> accountSpec = this.accountRepository.hasCriteria(query);

    Page<AccountEntity> accountPage = this.accountRepository.findAll(accountSpec, pageable);

    int page = accountPage.getNumber();
    int size = accountPage.getSize();
    int totalPages = accountPage.getTotalPages();
    List<AccountEntity> items = accountPage.getContent();

    List<AccountResponseDto> accountResponseDtos = items.stream()
        .map(item -> this.accountConverter.toAccountResponseDto(item)).toList();
    ResponseSpecification<AccountResponseDto> accountResponseSpecification = ResponseSpecification
        .<AccountResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(accountResponseDtos)
        .build();
    return ResponseDto.success(accountResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<AccountResponseDto>> findById(Long id) {
    AccountEntity accountEntity = this.accountRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (accountEntity == null) {
      return ResponseDto.notFound("Account not found");
    }

    AccountResponseDto accountnResponseDto = this.accountConverter.toAccountResponseDto(accountEntity);
    return ResponseDto.success(accountnResponseDto);
  }
}
