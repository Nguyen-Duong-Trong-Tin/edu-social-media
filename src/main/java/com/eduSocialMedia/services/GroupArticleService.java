package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleCreateDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleFindDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleResponseDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleUpdateDto;

public interface GroupArticleService {
  ResponseEntity<ResponseDto<GroupArticleResponseDto>> create(GroupArticleCreateDto createDto);

  ResponseEntity<ResponseDto<GroupArticleResponseDto>> update(Long id, GroupArticleUpdateDto updateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<GroupArticleResponseDto>>> find(GroupArticleFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<GroupArticleResponseDto>> findById(Long id);
}
