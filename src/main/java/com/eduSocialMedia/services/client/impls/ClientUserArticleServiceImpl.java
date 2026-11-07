package com.eduSocialMedia.services.client.impls;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduSocialMedia.converters.UserArticleConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.userArticles.ClientUserArticleCreateDto;
import com.eduSocialMedia.dtos.userArticles.ClientUserArticleUpdateDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleFindDto;
import com.eduSocialMedia.dtos.userArticles.UserArticleResponseDto;
import com.eduSocialMedia.enums.UserArticleStatusEnum;
import com.eduSocialMedia.repositories.UserArticleRepository;
import com.eduSocialMedia.repositories.entities.UserArticleEntity;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.client.ClientUserArticleService;

@Service
public class ClientUserArticleServiceImpl implements ClientUserArticleService {
  private final UserArticleConverter userArticleConverter;
  private final UserArticleRepository userArticleRepository;

  ClientUserArticleServiceImpl(UserArticleConverter userArticleConverter, UserArticleRepository userArticleRepository) {
    this.userArticleConverter = userArticleConverter;
    this.userArticleRepository = userArticleRepository;
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> create(ClientUserArticleCreateDto createDto,
      UserEntity me) {
    UserArticleEntity entity = this.userArticleConverter.toUserArticleEntity(createDto);
    entity.setUser(me);

    entity = this.userArticleRepository.save(entity);

    UserArticleResponseDto responseDto = this.userArticleConverter.toUserArticleResponseDto(entity);
    return ResponseDto.created(responseDto);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> update(Long id, ClientUserArticleUpdateDto updateDto,
      UserEntity me) {
    UserArticleEntity entity = this.userArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("User article not found");
    }

    if (!entity.getUser().getId().equals(me.getId())) {
      return ResponseDto.forbidden();
    }

    this.userArticleConverter.copyToUserArticleEntity(updateDto, entity);

    entity = this.userArticleRepository.save(entity);

    UserArticleResponseDto responseDto = this.userArticleConverter.toUserArticleResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto<Object>> delete(Long id, UserEntity me) {
    UserArticleEntity entity = this.userArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("User article not found");
    }

    if (!entity.getUser().getId().equals(me.getId())) {
      return ResponseDto.forbidden();
    }

    entity.setIsDeleted(true);
    this.userArticleRepository.save(entity);

    return ResponseDto.success(null);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<ResponseDto<ResponseSpecification<UserArticleResponseDto>>> find(UserArticleFindDto query,
      Pageable pageable, UserEntity me) {
    Specification<UserArticleEntity> spec = this.userArticleRepository.hasCriteria(query)
        .and(this.userArticleRepository.isVisibleTo(me.getId()));
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
  @Transactional(readOnly = true)
  public ResponseEntity<ResponseDto<UserArticleResponseDto>> findById(Long id, UserEntity me) {
    UserArticleEntity entity = this.userArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    // Someone else's non-public article is reported as missing, same as in find
    if (entity == null || !isVisibleTo(entity, me)) {
      return ResponseDto.notFound("User article not found");
    }

    UserArticleResponseDto responseDto = this.userArticleConverter.toUserArticleResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  private boolean isVisibleTo(UserArticleEntity entity, UserEntity me) {
    return entity.getStatus() == UserArticleStatusEnum.PUBLIC || entity.getUser().getId().equals(me.getId());
  }
}
