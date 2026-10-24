package com.eduSocialMedia.services.admin.impls;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.userArticleFiles.UserArticleFileCreateDto;
import com.eduSocialMedia.dtos.userArticleFiles.UserArticleFileResponseDto;
import com.eduSocialMedia.services.admin.AdminUserArticleFileService;

import jakarta.validation.Valid;

@Service("adminUserArticleFileServiceImpl")
public class AdminUserArticleFileServiceImpl implements AdminUserArticleFileService {
  @Override
  public ResponseEntity<ResponseDto<List<UserArticleFileResponseDto>>> create(
      @Valid UserArticleFileCreateDto userArticleFileCreateDto, List<MultipartFile> files) {
    

    return null;
  }
}
