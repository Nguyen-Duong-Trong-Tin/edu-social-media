package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionCreateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionFindDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionResponseDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionUpdateDto;

public interface GroupTaskSubmissionService {
  ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> create(GroupTaskSubmissionCreateDto createDto);

  ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> update(Long id, GroupTaskSubmissionUpdateDto updateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskSubmissionResponseDto>>> find(GroupTaskSubmissionFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> findById(Long id);
}
