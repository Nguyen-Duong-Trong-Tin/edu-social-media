package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.eduSocialMedia.converters.UserConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.users.UserCreateDto;
import com.eduSocialMedia.dtos.users.UserFindDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import com.eduSocialMedia.dtos.users.UserUpdateDto;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.UserService;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserConverter userConverter;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ResponseEntity<ResponseDto<UserResponseDto>> create(UserCreateDto userCreateDto) {
    UserEntity userExists = this.userRepository.findByEmail(userCreateDto.getEmail()).orElse(null);
    if (userExists != null) {
      return ResponseDto.badRequest(List.of("Email already exists"));
    }

    UserEntity userEntity = this.userConverter.toUserEntity(userCreateDto);
    userEntity.setPassword(this.passwordEncoder.encode(userCreateDto.getPassword()));
    userEntity.setSlug(generateUniqueSlug(userCreateDto.getFullName()));
    userEntity.setIsActive(true);
    userEntity.setIsDeleted(false);

    userEntity = this.userRepository.save(userEntity);

    UserResponseDto userResponseDto = this.userConverter.toUserResponseDto(userEntity);
    return ResponseDto.created(userResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<UserResponseDto>> update(Long id, UserUpdateDto userUpdateDto) {
    UserEntity userEntity = this.userRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    if (userUpdateDto.getFullName() != null && !userUpdateDto.getFullName().trim().isEmpty()
        && !userUpdateDto.getFullName().equals(userEntity.getFullName())) {
      userEntity.setSlug(generateUniqueSlug(userUpdateDto.getFullName()));
    }

    this.userConverter.copyToUserEntity(userUpdateDto, userEntity);

    userEntity = this.userRepository.save(userEntity);

    UserResponseDto userResponseDto = this.userConverter.toUserResponseDto(userEntity);
    return ResponseDto.success(userResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    UserEntity userEntity = this.userRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    userEntity.setIsDeleted(true);
    this.userRepository.save(userEntity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query, Pageable pageable) {
    Specification<UserEntity> userSpec = this.userRepository.hasCriteria(query);
    Page<UserEntity> userPage = this.userRepository.findAll(userSpec, pageable);

    int page = userPage.getNumber();
    int size = userPage.getSize();
    int totalPages = userPage.getTotalPages();
    List<UserEntity> items = userPage.getContent();

    List<UserResponseDto> userResponseDtos = items.stream()
        .map(item -> this.userConverter.toUserResponseDto(item)).toList();

    ResponseSpecification<UserResponseDto> userResponseSpecification = ResponseSpecification
        .<UserResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(userResponseDtos)
        .build();

    return ResponseDto.success(userResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<UserResponseDto>> findById(Long id) {
    UserEntity userEntity = this.userRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    UserResponseDto userResponseDto = this.userConverter.toUserResponseDto(userEntity);
    return ResponseDto.success(userResponseDto);
  }

  private String generateSlug(String fullName) {
    if (fullName == null) {
      return "";
    }
    String temp = fullName.toLowerCase().trim()
        .replaceAll("[^a-z0-9\\s-]", "")
        .replaceAll("[\\s-]+", "-");
    if (temp.startsWith("-")) {
      temp = temp.substring(1);
    }
    if (temp.endsWith("-")) {
      temp = temp.substring(0, temp.length() - 1);
    }
    return temp;
  }

  private String generateUniqueSlug(String fullName) {
    String baseSlug = generateSlug(fullName);
    String slug = baseSlug;
    int counter = 1;
    while (this.userRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }
}
