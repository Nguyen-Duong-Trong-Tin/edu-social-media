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
import com.eduSocialMedia.dtos.userArticles.UserArticleCreateDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleFindDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleResponseDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleUpdateDto;
import com.eduSocialMedia.services.UserArticleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/user-articles")
public class UserArticleController {
  @Autowired
  private UserArticleService userArticleService;

  @PostMapping
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> create(@Valid @RequestBody UserArticleCreateDto createDto) {
    return this.userArticleService.create(createDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody UserArticleUpdateDto updateDto) {
    return this.userArticleService.update(id, updateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.userArticleService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<UserArticleResponseDto>>> find(UserArticleFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.userArticleService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> findById(@PathVariable Long id) {
    return this.userArticleService.findById(id);
  }
}
