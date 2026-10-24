package com.eduSocialMedia.services.client;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.userArticleFiles.UserArticleFileCreateDto;
import com.eduSocialMedia.dtos.userArticleFiles.UserArticleFileResponseDto;

import jakarta.validation.Valid;

public interface ClientUserArticleFileService {
  ResponseEntity<ResponseDto<List<UserArticleFileResponseDto>>> create(
      @Valid @ModelAttribute UserArticleFileCreateDto userArticleFileCreateDto,
      @RequestParam List<MultipartFile> files);
}
