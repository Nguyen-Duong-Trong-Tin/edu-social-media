package com.eduSocialMedia.services.client;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTaskSubmissions.ClientGroupTaskSubmissionCreateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.ClientGroupTaskSubmissionUpdateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionFindDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionResponseDto;
import com.eduSocialMedia.repositories.entities.UserEntity;

public interface ClientGroupTaskSubmissionService {
  ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> create(ClientGroupTaskSubmissionCreateDto createDto,
      UserEntity me);

  ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> update(Long id,
      ClientGroupTaskSubmissionUpdateDto updateDto, UserEntity me);

  ResponseEntity<ResponseDto<Object>> delete(Long id, UserEntity me);

  ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskSubmissionResponseDto>>> find(
      GroupTaskSubmissionFindDto query, Pageable pageable, UserEntity me);

  ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> findById(Long id, UserEntity me);
}
