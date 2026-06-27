package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.users.UserCreateDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import com.eduSocialMedia.dtos.users.UserUpdateDto;
import com.eduSocialMedia.repositories.entities.UserEntity;

@Component
public class UserConverter {
  @Autowired
  private ModelMapper modelMapper;

  public UserEntity toUserEntity(UserCreateDto userCreateDto) {
    return this.modelMapper.map(userCreateDto, UserEntity.class);
  }

  public UserResponseDto toUserResponseDto(UserEntity userEntity) {
    return this.modelMapper.map(userEntity, UserResponseDto.class);
  }

  public UserEntity copyToUserEntity(UserUpdateDto userUpdateDto, UserEntity userEntity) {
    this.modelMapper.map(userUpdateDto, userEntity);
    return userEntity;
  }
}
