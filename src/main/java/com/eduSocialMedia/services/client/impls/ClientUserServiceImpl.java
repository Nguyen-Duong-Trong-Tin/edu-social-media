package com.eduSocialMedia.services.client.impls;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.eduSocialMedia.converters.UserConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.users.UserCreateDto;
import com.eduSocialMedia.dtos.users.UserFindDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import com.eduSocialMedia.dtos.users.UserUpdateDto;
import com.eduSocialMedia.dtos.users.UserUploadDto;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.CloudinaryService;
import com.eduSocialMedia.services.client.ClientUserService;
import com.eduSocialMedia.utils.SecurityUtil;
import com.eduSocialMedia.utils.TokenVersionUtil;

@Service
public class ClientUserServiceImpl implements ClientUserService {
  private final UserConverter userConverter;
  private final CloudinaryService cloudinaryService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SecurityUtil securityUtil;
  private final TokenVersionUtil tokenVersionUtil;

  ClientUserServiceImpl(UserConverter userConverter, CloudinaryService cloudinaryService, UserRepository userRepository,
      PasswordEncoder passwordEncoder, SecurityUtil securityUtil, TokenVersionUtil tokenVersionUtil) {
    this.userConverter = userConverter;
    this.cloudinaryService = cloudinaryService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.securityUtil = securityUtil;
    this.tokenVersionUtil = tokenVersionUtil;
  }

  @Override
  @Transactional
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

  // Not @Transactional: the new URL must be committed before the old image is deleted from Cloudinary,
  // and a DB connection should not be held during the upload
  @Override
  public ResponseEntity<ResponseDto<UserResponseDto>> upload(UserUploadDto userUploadDto, MultipartFile file) {
    ResponseEntity<ResponseDto<UserResponseDto>> checkPermission =
        this.securityUtil.validateUserOwnership(userUploadDto.getUserId());
    if (checkPermission != null) {
      return checkPermission;
    }

    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
      return ResponseDto.badRequest(List.of("File must be an image"));
    }

    UserEntity userEntity = this.userRepository.findByIdAndIsDeletedFalse(userUploadDto.getUserId()).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    String oldUrl = null;
    switch (userUploadDto.getType()) {
      case AVATAR:
        oldUrl = userEntity.getAvatar();
        break;
      case COVER_PHOTO:
        oldUrl = userEntity.getCoverPhoto();
        break;
      default:
        return ResponseDto.badRequest(List.of("Invalid upload type"));
    }

    String url = this.cloudinaryService.uploadFile(file);
    switch (userUploadDto.getType()) {
      case AVATAR:
        userEntity.setAvatar(url);
        break;
      case COVER_PHOTO:
        userEntity.setCoverPhoto(url);
        break;
    }

    userEntity = this.userRepository.save(userEntity);

    // Only remove the old image once the new one is uploaded and saved
    if (oldUrl != null && !oldUrl.trim().isEmpty()) {
      try {
        this.cloudinaryService.deleteFileIfExists(oldUrl);
      } catch (RuntimeException e) {
        // New image is already saved; an orphaned old file should not fail the request
      }
    }

    UserResponseDto userResponseDto = this.userConverter.toUserResponseDto(userEntity);
    return ResponseDto.success(userResponseDto);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto<UserResponseDto>> update(Long id, UserUpdateDto userUpdateDto) {
    ResponseEntity<ResponseDto<UserResponseDto>> checkPermission = this.securityUtil.validateUserOwnership(id);
    if (checkPermission != null) {
      return checkPermission;
    }

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
  @Transactional
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    UserEntity userEntity = this.userRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    userEntity.setIsDeleted(true);
    this.tokenVersionUtil.revokeTokens(userEntity);

    return ResponseDto.success(null);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query,
      Pageable pageable) {
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
  @Transactional(readOnly = true)
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
