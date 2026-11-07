package com.eduSocialMedia.controllers.client;

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

import com.eduSocialMedia.annotations.Auth;
import com.eduSocialMedia.annotations.CurrentUser;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.userArticles.ClientUserArticleCreateDto;
import com.eduSocialMedia.dtos.userArticles.ClientUserArticleUpdateDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleFindDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleResponseDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.client.ClientUserArticleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/user-articles")
public class ClientUserArticleController {
  private final ClientUserArticleService userArticleService;

  ClientUserArticleController(ClientUserArticleService userArticleService) {
    this.userArticleService = userArticleService;
  }

  @PostMapping
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> create(
      @Valid @RequestBody ClientUserArticleCreateDto createDto, @CurrentUser UserEntity me) {
    return this.userArticleService.create(createDto, me);
  }

  @PatchMapping("/{id}")
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody ClientUserArticleUpdateDto updateDto, @CurrentUser UserEntity me) {
    return this.userArticleService.update(id, updateDto, me);
  }

  @DeleteMapping("/{id}")
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id, @CurrentUser UserEntity me) {
    return this.userArticleService.delete(id, me);
  }

  @GetMapping
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<ResponseSpecification<UserArticleResponseDto>>> find(UserArticleFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, @CurrentUser UserEntity me) {
    return this.userArticleService.find(query, pageable, me);
  }

  @GetMapping("/{id}")
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> findById(@PathVariable Long id,
      @CurrentUser UserEntity me) {
    return this.userArticleService.findById(id, me);
  }
}
