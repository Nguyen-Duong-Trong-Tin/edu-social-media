package com.eduSocialMedia.services.admin.impls;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.GroupTaskSubmissionConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionCreateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionFindDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionResponseDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionUpdateDto;
import com.eduSocialMedia.repositories.GroupTaskRepository;
import com.eduSocialMedia.repositories.GroupTaskSubmissionRepository;
import com.eduSocialMedia.repositories.UserRepository;
import com.eduSocialMedia.repositories.entities.GroupTaskEntity;
import com.eduSocialMedia.repositories.entities.GroupTaskSubmissionEntity;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.admin.AdminGroupTaskSubmissionService;

@Service
public class AdminGroupTaskSubmissionServiceImpl implements AdminGroupTaskSubmissionService {
  private final GroupTaskSubmissionConverter groupTaskSubmissionConverter;
  private final GroupTaskSubmissionRepository groupTaskSubmissionRepository;
  private final GroupTaskRepository groupTaskRepository;
  private final UserRepository userRepository;

  AdminGroupTaskSubmissionServiceImpl(GroupTaskSubmissionConverter groupTaskSubmissionConverter,
      GroupTaskSubmissionRepository groupTaskSubmissionRepository, GroupTaskRepository groupTaskRepository,
      UserRepository userRepository) {
    this.groupTaskSubmissionConverter = groupTaskSubmissionConverter;
    this.groupTaskSubmissionRepository = groupTaskSubmissionRepository;
    this.groupTaskRepository = groupTaskRepository;
    this.userRepository = userRepository;
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

  @Override
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> create(GroupTaskSubmissionCreateDto createDto) {
    GroupTaskEntity groupTaskEntity = this.groupTaskRepository.findByIdAndIsDeletedFalse(createDto.getGroupTaskId())
        .orElse(null);
    if (groupTaskEntity == null) {
      return ResponseDto.notFound("Group task not found");
    }

    UserEntity userEntity = this.userRepository.findByIdAndIsDeletedFalse(createDto.getUserId()).orElse(null);
    if (userEntity == null) {
      return ResponseDto.notFound("User not found");
    }

    GroupTaskSubmissionEntity groupTaskSubmissionEntity = this.groupTaskSubmissionConverter
        .toGroupTaskSubmissionEntity(createDto);
    groupTaskSubmissionEntity.setGroupTask(groupTaskEntity);
    groupTaskSubmissionEntity.setUser(userEntity);
    groupTaskSubmissionEntity.setSlug(generateUniqueSlug(createDto.getName()));

    groupTaskSubmissionEntity = this.groupTaskSubmissionRepository.save(groupTaskSubmissionEntity);

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter
        .toGroupTaskSubmissionResponseDto(groupTaskSubmissionEntity);
    return ResponseDto.created(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> update(Long id,
      GroupTaskSubmissionUpdateDto updateDto) {
    GroupTaskSubmissionEntity groupTaskSubmissionEntity = this.groupTaskSubmissionRepository
        .findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupTaskSubmissionEntity == null) {
      return ResponseDto.notFound("Group task submission not found");
    }

    if (updateDto.getName() != null && !updateDto.getName().trim().isEmpty()
        && !updateDto.getName().equals(groupTaskSubmissionEntity.getName())) {
      groupTaskSubmissionEntity.setSlug(generateUniqueSlug(updateDto.getName()));
    }

    if (updateDto.getGroupTaskId() != null) {
      GroupTaskEntity groupTaskEntity = this.groupTaskRepository.findByIdAndIsDeletedFalse(updateDto.getGroupTaskId())
          .orElse(null);
      if (groupTaskEntity == null) {
        return ResponseDto.notFound("Group task not found");
      }
      groupTaskSubmissionEntity.setGroupTask(groupTaskEntity);
    }

    if (updateDto.getUserId() != null) {
      UserEntity userEntity = this.userRepository.findByIdAndIsDeletedFalse(updateDto.getUserId()).orElse(null);
      if (userEntity == null) {
        return ResponseDto.notFound("User not found");
      }
      groupTaskSubmissionEntity.setUser(userEntity);
    }

    this.groupTaskSubmissionConverter.copyToGroupTaskSubmissionEntity(updateDto, groupTaskSubmissionEntity);

    groupTaskSubmissionEntity = this.groupTaskSubmissionRepository.save(groupTaskSubmissionEntity);

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter
        .toGroupTaskSubmissionResponseDto(groupTaskSubmissionEntity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    GroupTaskSubmissionEntity groupTaskSubmissionEntity = this.groupTaskSubmissionRepository
        .findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupTaskSubmissionEntity == null) {
      return ResponseDto.notFound("Group task submission not found");
    }

    groupTaskSubmissionEntity.setIsDeleted(true);
    this.groupTaskSubmissionRepository.save(groupTaskSubmissionEntity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskSubmissionResponseDto>>> find(
      GroupTaskSubmissionFindDto query, Pageable pageable) {
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
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> findById(Long id) {
    GroupTaskSubmissionEntity groupTaskSubmissionEntity = this.groupTaskSubmissionRepository
        .findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupTaskSubmissionEntity == null) {
      return ResponseDto.notFound("Group task submission not found");
    }

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter
        .toGroupTaskSubmissionResponseDto(groupTaskSubmissionEntity);
    return ResponseDto.success(responseDto);
  }
}
