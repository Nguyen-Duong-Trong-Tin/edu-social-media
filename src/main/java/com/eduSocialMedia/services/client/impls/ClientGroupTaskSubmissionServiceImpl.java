package com.eduSocialMedia.services.client.impls;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduSocialMedia.converters.GroupTaskSubmissionConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTaskSubmissions.ClientGroupTaskSubmissionCreateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.ClientGroupTaskSubmissionUpdateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionFindDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionResponseDto;
import com.eduSocialMedia.repositories.GroupTaskRepository;
import com.eduSocialMedia.repositories.GroupTaskSubmissionRepository;
import com.eduSocialMedia.repositories.entities.GroupTaskEntity;
import com.eduSocialMedia.repositories.entities.GroupTaskSubmissionEntity;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.client.ClientGroupTaskSubmissionService;

@Service
public class ClientGroupTaskSubmissionServiceImpl implements ClientGroupTaskSubmissionService {
  private final GroupTaskSubmissionConverter groupTaskSubmissionConverter;
  private final GroupTaskSubmissionRepository groupTaskSubmissionRepository;
  private final GroupTaskRepository groupTaskRepository;

  ClientGroupTaskSubmissionServiceImpl(GroupTaskSubmissionConverter groupTaskSubmissionConverter,
      GroupTaskSubmissionRepository groupTaskSubmissionRepository, GroupTaskRepository groupTaskRepository) {
    this.groupTaskSubmissionConverter = groupTaskSubmissionConverter;
    this.groupTaskSubmissionRepository = groupTaskSubmissionRepository;
    this.groupTaskRepository = groupTaskRepository;
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> create(
      ClientGroupTaskSubmissionCreateDto createDto, UserEntity me) {
    GroupTaskEntity groupTask = this.groupTaskRepository.findByIdAndIsDeletedFalse(createDto.getGroupTaskId())
        .orElse(null);
    if (groupTask == null) {
      return ResponseDto.notFound("Group task not found");
    }

    GroupTaskSubmissionEntity entity = this.groupTaskSubmissionConverter.toGroupTaskSubmissionEntity(createDto);
    entity.setGroupTask(groupTask);
    entity.setUser(me);
    entity.setSlug(generateUniqueSlug(createDto.getName()));

    entity = this.groupTaskSubmissionRepository.save(entity);

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter
        .toGroupTaskSubmissionResponseDto(entity);
    return ResponseDto.created(responseDto);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> update(Long id,
      ClientGroupTaskSubmissionUpdateDto updateDto, UserEntity me) {
    GroupTaskSubmissionEntity entity = this.groupTaskSubmissionRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group task submission not found");
    }

    if (!entity.getUser().getId().equals(me.getId())) {
      return ResponseDto.forbidden();
    }

    if (updateDto.getName() != null && !updateDto.getName().trim().isEmpty()
        && !updateDto.getName().equals(entity.getName())) {
      entity.setSlug(generateUniqueSlug(updateDto.getName()));
    }

    if (updateDto.getGroupTaskId() != null) {
      GroupTaskEntity groupTask = this.groupTaskRepository.findByIdAndIsDeletedFalse(updateDto.getGroupTaskId())
          .orElse(null);
      if (groupTask == null) {
        return ResponseDto.notFound("Group task not found");
      }
      entity.setGroupTask(groupTask);
    }

    this.groupTaskSubmissionConverter.copyToGroupTaskSubmissionEntity(updateDto, entity);

    entity = this.groupTaskSubmissionRepository.save(entity);

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter
        .toGroupTaskSubmissionResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseDto<Object>> delete(Long id, UserEntity me) {
    GroupTaskSubmissionEntity entity = this.groupTaskSubmissionRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group task submission not found");
    }

    if (!entity.getUser().getId().equals(me.getId())) {
      return ResponseDto.forbidden();
    }

    entity.setIsDeleted(true);
    this.groupTaskSubmissionRepository.save(entity);

    return ResponseDto.success(null);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskSubmissionResponseDto>>> find(
      GroupTaskSubmissionFindDto query, Pageable pageable, UserEntity me) {
    // Users only see their own submissions, whatever userId the query asks for
    query.setUserId(me.getId());
    Specification<GroupTaskSubmissionEntity> spec = this.groupTaskSubmissionRepository.hasCriteria(query);
    Page<GroupTaskSubmissionEntity> pageResult = this.groupTaskSubmissionRepository.findAll(spec, pageable);

    int page = pageResult.getNumber();
    int size = pageResult.getSize();
    int totalPages = pageResult.getTotalPages();
    List<GroupTaskSubmissionEntity> items = pageResult.getContent();

    List<GroupTaskSubmissionResponseDto> responseDtos = items.stream()
        .map(item -> this.groupTaskSubmissionConverter.toGroupTaskSubmissionResponseDto(item)).toList();

    ResponseSpecification<GroupTaskSubmissionResponseDto> specResult = ResponseSpecification
        .<GroupTaskSubmissionResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(responseDtos)
        .build();

    return ResponseDto.success(specResult);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> findById(Long id, UserEntity me) {
    GroupTaskSubmissionEntity entity = this.groupTaskSubmissionRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    // Someone else's submission is reported as missing, same as in find
    if (entity == null || !entity.getUser().getId().equals(me.getId())) {
      return ResponseDto.notFound("Group task submission not found");
    }

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter
        .toGroupTaskSubmissionResponseDto(entity);
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
    while (this.groupTaskSubmissionRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }
}
