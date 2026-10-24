package com.eduSocialMedia.services.admin.impls;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.GroupTaskConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskCreateDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskFindDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskResponseDto;
import com.eduSocialMedia.dtos.groupTasks.GroupTaskUpdateDto;
import com.eduSocialMedia.repositories.GroupRepository;
import com.eduSocialMedia.repositories.GroupTaskRepository;
import com.eduSocialMedia.repositories.entities.GroupEntity;
import com.eduSocialMedia.repositories.entities.GroupTaskEntity;
import com.eduSocialMedia.services.admin.AdminGroupTaskService;

@Service
public class AdminGroupTaskServiceImpl implements AdminGroupTaskService {
  private final GroupTaskConverter groupTaskConverter;
  private final GroupTaskRepository groupTaskRepository;
  private final GroupRepository groupRepository;

  AdminGroupTaskServiceImpl(GroupTaskConverter groupTaskConverter, GroupTaskRepository groupTaskRepository,
      GroupRepository groupRepository) {
    this.groupTaskConverter = groupTaskConverter;
    this.groupTaskRepository = groupTaskRepository;
    this.groupRepository = groupRepository;
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
    while (this.groupTaskRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }

  @Override
  public ResponseEntity<ResponseDto<GroupTaskResponseDto>> create(GroupTaskCreateDto createDto) {
    GroupEntity groupEntity = this.groupRepository.findByIdAndIsDeletedFalse(createDto.getGroupId()).orElse(null);
    if (groupEntity == null) {
      return ResponseDto.notFound("Group not found");
    }

    GroupTaskEntity groupTaskEntity = this.groupTaskConverter.toGroupTaskEntity(createDto);
    groupTaskEntity.setGroup(groupEntity);
    groupTaskEntity.setSlug(generateUniqueSlug(createDto.getName()));

    groupTaskEntity = this.groupTaskRepository.save(groupTaskEntity);

    GroupTaskResponseDto responseDto = this.groupTaskConverter.toGroupTaskResponseDto(groupTaskEntity);
    return ResponseDto.created(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupTaskResponseDto>> update(Long id, GroupTaskUpdateDto updateDto) {
    GroupTaskEntity groupTaskEntity = this.groupTaskRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupTaskEntity == null) {
      return ResponseDto.notFound("Group task not found");
    }

    if (updateDto.getName() != null && !updateDto.getName().trim().isEmpty()
        && !updateDto.getName().equals(groupTaskEntity.getName())) {
      groupTaskEntity.setSlug(generateUniqueSlug(updateDto.getName()));
    }

    if (updateDto.getGroupId() != null) {
      GroupEntity groupEntity = this.groupRepository.findByIdAndIsDeletedFalse(updateDto.getGroupId()).orElse(null);
      if (groupEntity == null) {
        return ResponseDto.notFound("Group not found");
      }
      groupTaskEntity.setGroup(groupEntity);
    }

    this.groupTaskConverter.copyToGroupTaskEntity(updateDto, groupTaskEntity);

    groupTaskEntity = this.groupTaskRepository.save(groupTaskEntity);
    GroupTaskResponseDto responseDto = this.groupTaskConverter.toGroupTaskResponseDto(groupTaskEntity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    GroupTaskEntity groupTaskEntity = this.groupTaskRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupTaskEntity == null) {
      return ResponseDto.notFound("Group task not found");
    }

    groupTaskEntity.setIsDeleted(true);
    this.groupTaskRepository.save(groupTaskEntity);

    return ResponseDto.success("Group task deleted successfully");
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskResponseDto>>> find(GroupTaskFindDto query,
      Pageable pageable) {
    Specification<GroupTaskEntity> spec = this.groupTaskRepository.hasCriteria(query);
    Page<GroupTaskEntity> pageResult = this.groupTaskRepository.findAll(spec, pageable);

    int page = pageResult.getNumber();
    int size = pageResult.getSize();
    int totalPages = pageResult.getTotalPages();
    List<GroupTaskEntity> items = pageResult.getContent();

    List<GroupTaskResponseDto> responseDtos = items.stream()
        .map(item -> this.groupTaskConverter.toGroupTaskResponseDto(item)).toList();

    ResponseSpecification<GroupTaskResponseDto> specResult = ResponseSpecification
        .<GroupTaskResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(responseDtos)
        .build();

    return ResponseDto.success(specResult);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupTaskResponseDto>> findById(Long id) {
    GroupTaskEntity groupTaskEntity = this.groupTaskRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupTaskEntity == null) {
      return ResponseDto.notFound("Group task not found");
    }

    GroupTaskResponseDto responseDto = this.groupTaskConverter.toGroupTaskResponseDto(groupTaskEntity);
    return ResponseDto.success(responseDto);
  }
}