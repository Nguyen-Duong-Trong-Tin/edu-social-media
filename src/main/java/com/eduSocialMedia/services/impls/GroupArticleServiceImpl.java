package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.GroupArticleConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleCreateDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleFindDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleResponseDto;
import com.eduSocialMedia.dtos.groupArticles.GroupArticleUpdateDto;
import com.eduSocialMedia.repositories.GroupArticleRepository;
import com.eduSocialMedia.repositories.GroupRepository;
import com.eduSocialMedia.repositories.entities.GroupArticleEntity;
import com.eduSocialMedia.repositories.entities.GroupEntity;
import com.eduSocialMedia.services.GroupArticleService;

@Service
public class GroupArticleServiceImpl implements GroupArticleService {
  @Autowired
  private GroupArticleConverter groupArticleConverter;

  @Autowired
  private GroupArticleRepository groupArticleRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Override
  public ResponseEntity<ResponseDto<GroupArticleResponseDto>> create(GroupArticleCreateDto createDto) {
    GroupEntity group = this.groupRepository.findByIdAndIsDeletedFalse(createDto.getGroupId()).orElse(null);
    if (group == null) {
      return ResponseDto.notFound("Group not found");
    }

    GroupArticleEntity entity = this.groupArticleConverter.toGroupArticleEntity(createDto);
    entity.setGroup(group);
    entity.setSlug(generateUniqueSlug(createDto.getName()));

    entity = this.groupArticleRepository.save(entity);

    GroupArticleResponseDto responseDto = this.groupArticleConverter.toGroupArticleResponseDto(entity);
    return ResponseDto.created(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupArticleResponseDto>> update(Long id, GroupArticleUpdateDto updateDto) {
    GroupArticleEntity entity = this.groupArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group article not found");
    }

    if (updateDto.getName() != null && !updateDto.getName().trim().isEmpty()
        && !updateDto.getName().equals(entity.getName())) {
      entity.setSlug(generateUniqueSlug(updateDto.getName()));
    }

    if (updateDto.getGroupId() != null) {
      GroupEntity group = this.groupRepository.findByIdAndIsDeletedFalse(updateDto.getGroupId()).orElse(null);
      if (group == null) {
        return ResponseDto.notFound("Group not found");
      }
      entity.setGroup(group);
    }

    this.groupArticleConverter.copyToGroupArticleEntity(updateDto, entity);

    entity = this.groupArticleRepository.save(entity);

    GroupArticleResponseDto responseDto = this.groupArticleConverter.toGroupArticleResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    GroupArticleEntity entity = this.groupArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group article not found");
    }

    entity.setIsDeleted(true);
    this.groupArticleRepository.save(entity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupArticleResponseDto>>> find(GroupArticleFindDto query, Pageable pageable) {
    Specification<GroupArticleEntity> spec = this.groupArticleRepository.hasCriteria(query);
    Page<GroupArticleEntity> pageResult = this.groupArticleRepository.findAll(spec, pageable);

    int page = pageResult.getNumber();
    int size = pageResult.getSize();
    int totalPages = pageResult.getTotalPages();
    List<GroupArticleEntity> items = pageResult.getContent();

    List<GroupArticleResponseDto> responseDtos = items.stream()
        .map(item -> this.groupArticleConverter.toGroupArticleResponseDto(item)).toList();

    ResponseSpecification<GroupArticleResponseDto> specResult = ResponseSpecification
        .<GroupArticleResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(responseDtos)
        .build();

    return ResponseDto.success(specResult);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupArticleResponseDto>> findById(Long id) {
    GroupArticleEntity entity = this.groupArticleRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group article not found");
    }

    GroupArticleResponseDto responseDto = this.groupArticleConverter.toGroupArticleResponseDto(entity);
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
    while (this.groupArticleRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }
}
