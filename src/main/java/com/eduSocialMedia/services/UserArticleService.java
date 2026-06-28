package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.userArticles.UserArticleCreateDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleFindDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleResponseDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleUpdateDto;

public interface UserArticleService {
  ResponseEntity<ResponseDto<UserArticleResponseDto>> create(UserArticleCreateDto createDto);

  ResponseEntity<ResponseDto<UserArticleResponseDto>> update(Long id, UserArticleUpdateDto updateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<UserArticleResponseDto>>> find(UserArticleFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<UserArticleResponseDto>> findById(Long id);
}
