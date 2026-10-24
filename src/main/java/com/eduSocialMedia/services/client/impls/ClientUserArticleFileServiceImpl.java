package com.eduSocialMedia.services.client.impls;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.userArticleFiles.UserArticleFileCreateDto;
import com.eduSocialMedia.dtos.userArticleFiles.UserArticleFileResponseDto;
import com.eduSocialMedia.services.client.ClientUserArticleFileService;

import jakarta.validation.Valid;

@Service
public class ClientUserArticleFileServiceImpl implements ClientUserArticleFileService {
  @Override
  public ResponseEntity<ResponseDto<List<UserArticleFileResponseDto>>> create(
      @Valid UserArticleFileCreateDto userArticleFileCreateDto, List<MultipartFile> files) {
    

    return null;
  }
}
