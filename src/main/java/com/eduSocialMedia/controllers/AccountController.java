package com.eduSocialMedia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.accounts.AccountCreateDto;
import com.eduSocialMedia.dtos.accounts.AccountFindDto;
import com.eduSocialMedia.dtos.accounts.AccountResponseDto;
import com.eduSocialMedia.dtos.accounts.AccountUpdateDto;
import com.eduSocialMedia.services.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
  @Autowired
  private AccountService accountService;

  @PostMapping
  public ResponseEntity<ResponseDto<AccountResponseDto>> create(@Valid @RequestBody AccountCreateDto accountCreateDto) {
    return this.accountService.create(accountCreateDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<AccountResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody AccountUpdateDto accountUpdateDto) {
    return this.accountService.update(id, accountUpdateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.accountService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<AccountResponseDto>>> find(AccountFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.accountService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<AccountResponseDto>> findById(@PathVariable Long id) {
    return this.accountService.findById(id);
  }
}
