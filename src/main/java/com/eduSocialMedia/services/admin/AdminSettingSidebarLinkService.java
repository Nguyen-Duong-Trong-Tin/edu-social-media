package com.eduSocialMedia.services.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkCreateDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkFindDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkResponseDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkUpdateDto;

public interface AdminSettingSidebarLinkService {
  ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> create(SettingSidebarLinkCreateDto createDto);

  ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> update(Long id, SettingSidebarLinkUpdateDto updateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<SettingSidebarLinkResponseDto>>> find(SettingSidebarLinkFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> findById(Long id);
}
