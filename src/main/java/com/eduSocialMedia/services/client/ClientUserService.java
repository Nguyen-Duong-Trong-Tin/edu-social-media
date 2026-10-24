package com.eduSocialMedia.services.client;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.users.UserCreateDto;
import com.eduSocialMedia.dtos.users.UserFindDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import com.eduSocialMedia.dtos.users.UserUpdateDto;
import com.eduSocialMedia.dtos.users.UserUploadDto;

public interface ClientUserService {
  ResponseEntity<ResponseDto<UserResponseDto>> create(UserCreateDto userCreateDto);

  ResponseEntity<ResponseDto<UserResponseDto>> upload(UserUploadDto userUploadDto, MultipartFile file);

  ResponseEntity<ResponseDto<UserResponseDto>> update(Long id, UserUpdateDto userUpdateDto);

  ResponseEntity<ResponseDto<Object>> delete(Long id);

  ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query, Pageable pageable);

  ResponseEntity<ResponseDto<UserResponseDto>> findById(Long id);
}

