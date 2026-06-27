package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicCreateDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicFindDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicResponseDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicUpdateDto;

public interface GroupTopicService {
  ResponseEntity<ResponseDto<GroupTopicResponseDto>> create(GroupTopicCreateDto groupTopicCreateDto);

  ResponseEntity<ResponseDto<GroupTopicResponseDto>> update(Long id, GroupTopicUpdateDto groupTopicUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<GroupTopicResponseDto>>> find(GroupTopicFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<GroupTopicResponseDto>> findById(Long id);
}
