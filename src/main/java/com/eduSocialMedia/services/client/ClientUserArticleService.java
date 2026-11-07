package com.eduSocialMedia.services.client;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.userArticles.ClientUserArticleCreateDto;
import com.eduSocialMedia.dtos.userArticles.ClientUserArticleUpdateDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleFindDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleResponseDto;
import com.eduSocialMedia.repositories.entities.UserEntity;

public interface ClientUserArticleService {
  ResponseEntity<ResponseDto<UserArticleResponseDto>> create(ClientUserArticleCreateDto createDto, UserEntity me);

  ResponseEntity<ResponseDto<UserArticleResponseDto>> update(Long id, ClientUserArticleUpdateDto updateDto,
      UserEntity me);

  ResponseEntity<ResponseDto<Object>> delete(Long id, UserEntity me);

  ResponseEntity<ResponseDto<ResponseSpecification<UserArticleResponseDto>>> find(UserArticleFindDto query,
      Pageable pageable, UserEntity me);

  ResponseEntity<ResponseDto<UserArticleResponseDto>> findById(Long id, UserEntity me);
}
