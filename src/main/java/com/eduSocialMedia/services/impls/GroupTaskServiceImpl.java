package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.eduSocialMedia.services.GroupTaskService;

@Service
public class GroupTaskServiceImpl implements GroupTaskService {
  @Autowired
  private GroupTaskConverter groupTaskConverter;

  @Autowired
  private GroupTaskRepository groupTaskRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Override
  public ResponseEntity<ResponseDto<GroupTaskResponseDto>> create(GroupTaskCreateDto createDto) {
    GroupEntity group = this.groupRepository.findByIdAndIsDeletedFalse(createDto.getGroupId()).orElse(null);
    if (group == null) {
      return ResponseDto.notFound("Group not found");
    }

    GroupTaskEntity entity = this.groupTaskConverter.toGroupTaskEntity(createDto);
    entity.setGroup(group);
    entity.setSlug(generateUniqueSlug(createDto.getName()));

    entity = this.groupTaskRepository.save(entity);

    GroupTaskResponseDto responseDto = this.groupTaskConverter.toGroupTaskResponseDto(entity);
    return ResponseDto.created(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupTaskResponseDto>> update(Long id, GroupTaskUpdateDto updateDto) {
    GroupTaskEntity entity = this.groupTaskRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group task not found");
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

    this.groupTaskConverter.copyToGroupTaskEntity(updateDto, entity);

    entity = this.groupTaskRepository.save(entity);

    GroupTaskResponseDto responseDto = this.groupTaskConverter.toGroupTaskResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    GroupTaskEntity entity = this.groupTaskRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group task not found");
    }

    entity.setIsDeleted(true);
    this.groupTaskRepository.save(entity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskResponseDto>>> find(GroupTaskFindDto query, Pageable pageable) {
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
    GroupTaskEntity entity = this.groupTaskRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group task not found");
    }

    GroupTaskResponseDto responseDto = this.groupTaskConverter.toGroupTaskResponseDto(entity);
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
    while (this.groupTaskRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }
}
