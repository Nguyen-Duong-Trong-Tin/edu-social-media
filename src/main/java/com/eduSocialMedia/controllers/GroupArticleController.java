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
import com.eduSocialMedia.dtos.groupArticles.GroupArticleCreateDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleFindDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleResponseDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleUpdateDto;
import com.eduSocialMedia.services.GroupArticleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/group-articles")
public class GroupArticleController {
  @Autowired
  private GroupArticleService groupArticleService;

  @PostMapping
  public ResponseEntity<ResponseDto<GroupArticleResponseDto>> create(@Valid @RequestBody GroupArticleCreateDto createDto) {
    return this.groupArticleService.create(createDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupArticleResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody GroupArticleUpdateDto updateDto) {
    return this.groupArticleService.update(id, updateDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.groupArticleService.delete(id);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupArticleResponseDto>>> find(GroupArticleFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.groupArticleService.find(query, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<GroupArticleResponseDto>> findById(@PathVariable Long id) {
    return this.groupArticleService.findById(id);
  }
}
