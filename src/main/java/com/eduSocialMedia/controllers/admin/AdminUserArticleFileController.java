package com.eduSocialMedia.controllers.admin;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eduSocialMedia.contants.Constant;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.userArticleFiles.UserArticleFileCreateDto;
import com.eduSocialMedia.dtos.userArticleFiles.UserArticleFileResponseDto;
import com.eduSocialMedia.services.admin.AdminUserArticleFileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/admin/user-article-files")
public class AdminUserArticleFileController {
  private final AdminUserArticleFileService userArticleFileService;

  AdminUserArticleFileController(AdminUserArticleFileService userArticleFileService) {
    this.userArticleFileService = userArticleFileService;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseDto<List<UserArticleFileResponseDto>>> create(
    @Valid @ModelAttribute UserArticleFileCreateDto userArticleFileCreateDto,
    @RequestParam List<MultipartFile> files
  ) {
    if (files.size() > Constant.MAX_ARTICLE_IMAGES) {
      return ResponseDto.badRequest(List.of("Too many images"));
    }

    return userArticleFileService.create(userArticleFileCreateDto, files);
  }
}
