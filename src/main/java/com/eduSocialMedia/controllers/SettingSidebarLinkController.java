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

import com.eduSocialMedia.annotations.Auth;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkCreateDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkFindDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkResponseDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkUpdateDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.services.SettingSidebarLinkService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/setting-sidebar-links")
public class SettingSidebarLinkController {
  private final SettingSidebarLinkService settingSidebarLinkService;

  SettingSidebarLinkController(SettingSidebarLinkService settingSidebarLinkService) {
    this.settingSidebarLinkService = settingSidebarLinkService;
  }

  @PostMapping
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> create(@Valid @RequestBody SettingSidebarLinkCreateDto createDto) {
    return this.settingSidebarLinkService.create(createDto);
  }

  @PatchMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> update(@PathVariable Long id, @Valid @RequestBody SettingSidebarLinkUpdateDto updateDto) {
    return this.settingSidebarLinkService.update(id, updateDto);
  }

  @DeleteMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.settingSidebarLinkService.delete(id);
  }

  @GetMapping
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<ResponseSpecification<SettingSidebarLinkResponseDto>>> find(
      SettingSidebarLinkFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.settingSidebarLinkService.find(query, pageable);
  }

  @GetMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> findById(@PathVariable Long id) {
    return this.settingSidebarLinkService.findById(id);
  }
}
