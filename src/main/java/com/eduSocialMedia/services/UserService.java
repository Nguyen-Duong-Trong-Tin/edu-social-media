package com.eduSocialMedia.services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.users.UserCreateDto;
import com.eduSocialMedia.dtos.users.UserFindDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import com.eduSocialMedia.dtos.users.UserUpdateDto;

public interface UserService {
  ResponseEntity<ResponseDto<UserResponseDto>> create(UserCreateDto userCreateDto);

  ResponseEntity<ResponseDto<UserResponseDto>> update(Long id, UserUpdateDto userUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<UserResponseDto>> findById(Long id);
}
