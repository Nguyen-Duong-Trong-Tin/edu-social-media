package com.eduSocialMedia.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eduSocialMedia.dtos.auth.AuthRegisterDto;
import com.eduSocialMedia.dtos.auth.AuthRegisterResponseDto;
import com.eduSocialMedia.dtos.users.UserCreateDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import com.eduSocialMedia.dtos.users.UserUpdateDto;
import com.eduSocialMedia.repositories.entities.UserEntity;

@Component
public class UserConverter {
  private final ModelMapper modelMapper;

  UserConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public UserEntity toUserEntity(UserCreateDto userCreateDto) {
    return this.modelMapper.map(userCreateDto, UserEntity.class);
  }

  public UserEntity toUserEntity(AuthRegisterDto authRegisterDto) {
    return this.modelMapper.map(authRegisterDto, UserEntity.class);
  }

  public UserResponseDto toUserResponseDto(UserEntity userEntity) {
    return this.modelMapper.map(userEntity, UserResponseDto.class);
  }

  public AuthRegisterResponseDto toAuthRegisterResponseDto(UserEntity userEntity) {
    return this.modelMapper.map(userEntity, AuthRegisterResponseDto.class);
  }
  
  public UserEntity copyToUserEntity(UserUpdateDto userUpdateDto, UserEntity userEntity) {
    this.modelMapper.map(userUpdateDto, userEntity);
    return userEntity;
  }
}
