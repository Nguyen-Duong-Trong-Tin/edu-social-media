package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.UserArticleConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.userArticles.UserArticleCreateDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleFindDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleResponseDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleUpdateDto;
import com.eduSocialMedia.repositories.UserArticleRepository;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.UserArticleEntity;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.UserArticleService;

@Service
public class UserArticleServiceImpl implements UserArticleService {
  @Autowired
  private UserArticleConverter userArticleConverter;

  @Autowired
  private UserArticleRepository userArticleRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> create(UserArticleCreateDto createDto) {
    UserEntity user = this.userRepository.findByIdAndIsDeletedFalse(createDto.getUserId()).orElse(null);
    if (user == null) {
      return ResponseDto.notFound("User not found");
    }

    UserArticleEntity entity = this.userArticleConverter.toUserArticleEntity(createDto);
    entity.setUser(user);
    entity.setSlug(generateUniqueSlug(createDto.getName()));

    entity = this.userArticleRepository.save(entity);

    UserArticleResponseDto responseDto = this.userArticleConverter.toUserArticleResponseDto(entity);
    return ResponseDto.created(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> update(Long id, UserArticleUpdateDto updateDto) {
    UserArticleEntity entity = this.userArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("User article not found");
    }

    if (updateDto.getName() != null && !updateDto.getName().trim().isEmpty()
        && !updateDto.getName().equals(entity.getName())) {
      entity.setSlug(generateUniqueSlug(updateDto.getName()));
    }

    if (updateDto.getUserId() != null) {
      UserEntity user = this.userRepository.findByIdAndIsDeletedFalse(updateDto.getUserId()).orElse(null);
      if (user == null) {
        return ResponseDto.notFound("User not found");
      }
      entity.setUser(user);
    }

    this.userArticleConverter.copyToUserArticleEntity(updateDto, entity);

    entity = this.userArticleRepository.save(entity);

    UserArticleResponseDto responseDto = this.userArticleConverter.toUserArticleResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    UserArticleEntity entity = this.userArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("User article not found");
    }

    entity.setIsDeleted(true);
    this.userArticleRepository.save(entity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<UserArticleResponseDto>>> find(UserArticleFindDto query, Pageable pageable) {
    Specification<UserArticleEntity> spec = this.userArticleRepository.hasCriteria(query);
    Page<UserArticleEntity> pageResult = this.userArticleRepository.findAll(spec, pageable);

    int page = pageResult.getNumber();
    int size = pageResult.getSize();
    int totalPages = pageResult.getTotalPages();
    List<UserArticleEntity> items = pageResult.getContent();

    List<UserArticleResponseDto> responseDtos = items.stream()
        .map(item -> this.userArticleConverter.toUserArticleResponseDto(item)).toList();

    ResponseSpecification<UserArticleResponseDto> specResult = ResponseSpecification
        .<UserArticleResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(responseDtos)
        .build();

    return ResponseDto.success(specResult);
  }

  @Override
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> findById(Long id) {
    UserArticleEntity entity = this.userArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("User article not found");
    }

    UserArticleResponseDto responseDto = this.userArticleConverter.toUserArticleResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  private String generateSlug(String name) {
    if (name == null) {
      return "";
    }
    String temp = name.toLowerCase().trim()
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

  private String generateUniqueSlug(String name) {
    String baseSlug = generateSlug(name);
    String slug = baseSlug;
    int counter = 1;
    while (this.userArticleRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }
}
