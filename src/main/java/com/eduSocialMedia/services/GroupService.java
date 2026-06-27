package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groups.GroupCreateDto;
import com.eduSocialMedia.dtos.groups.GroupFindDto;
import com.eduSocialMedia.dtos.groups.GroupResponseDto;
import com.eduSocialMedia.dtos.groups.GroupUpdateDto;

public interface GroupService {
  ResponseEntity<ResponseDto<GroupResponseDto>> create(GroupCreateDto groupCreateDto);

  ResponseEntity<ResponseDto<GroupResponseDto>> update(Long id, GroupUpdateDto groupUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<GroupResponseDto>>> find(GroupFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<GroupResponseDto>> findById(Long id);
}
