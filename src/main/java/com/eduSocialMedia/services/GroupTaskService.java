package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskCreateDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskFindDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskResponseDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskUpdateDto;

public interface GroupTaskService {
  ResponseEntity<ResponseDto<GroupTaskResponseDto>> create(GroupTaskCreateDto createDto);

  ResponseEntity<ResponseDto<GroupTaskResponseDto>> update(Long id, GroupTaskUpdateDto updateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskResponseDto>>> find(GroupTaskFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<GroupTaskResponseDto>> findById(Long id);
}
