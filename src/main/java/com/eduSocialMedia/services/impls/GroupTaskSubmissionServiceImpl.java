package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.eduSocialMedia.services.GroupTaskSubmissionService;

@Service
public class GroupTaskSubmissionServiceImpl implements GroupTaskSubmissionService {
  @Autowired
  private GroupTaskSubmissionConverter groupTaskSubmissionConverter;

  @Autowired
  private GroupTaskSubmissionRepository groupTaskSubmissionRepository;

  @Autowired
  private GroupTaskRepository groupTaskRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> create(GroupTaskSubmissionCreateDto createDto) {
    GroupTaskEntity groupTask = this.groupTaskRepository.findByIdAndIsDeletedFalse(createDto.getGroupTaskId()).orElse(null);
    if (groupTask == null) {
      return ResponseDto.notFound("Group task not found");
    }

    UserEntity user = this.userRepository.findByIdAndIsDeletedFalse(createDto.getUserId()).orElse(null);
    if (user == null) {
      return ResponseDto.notFound("User not found");
    }

    GroupTaskSubmissionEntity entity = this.groupTaskSubmissionConverter.toGroupTaskSubmissionEntity(createDto);
    entity.setGroupTask(groupTask);
    entity.setUser(user);
    entity.setSlug(generateUniqueSlug(createDto.getName()));

    entity = this.groupTaskSubmissionRepository.save(entity);

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter.toGroupTaskSubmissionResponseDto(entity);
    return ResponseDto.created(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> update(Long id, GroupTaskSubmissionUpdateDto updateDto) {
    GroupTaskSubmissionEntity entity = this.groupTaskSubmissionRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group task submission not found");
    }

    if (updateDto.getName() != null && !updateDto.getName().trim().isEmpty()
        && !updateDto.getName().equals(entity.getName())) {
      entity.setSlug(generateUniqueSlug(updateDto.getName()));
    }

    if (updateDto.getGroupTaskId() != null) {
      GroupTaskEntity groupTask = this.groupTaskRepository.findByIdAndIsDeletedFalse(updateDto.getGroupTaskId()).orElse(null);
      if (groupTask == null) {
        return ResponseDto.notFound("Group task not found");
      }
      entity.setGroupTask(groupTask);
    }

    if (updateDto.getUserId() != null) {
      UserEntity user = this.userRepository.findByIdAndIsDeletedFalse(updateDto.getUserId()).orElse(null);
      if (user == null) {
        return ResponseDto.notFound("User not found");
      }
      entity.setUser(user);
    }

    this.groupTaskSubmissionConverter.copyToGroupTaskSubmissionEntity(updateDto, entity);

    entity = this.groupTaskSubmissionRepository.save(entity);

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter.toGroupTaskSubmissionResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    GroupTaskSubmissionEntity entity = this.groupTaskSubmissionRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group task submission not found");
    }

    entity.setIsDeleted(true);
    this.groupTaskSubmissionRepository.save(entity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskSubmissionResponseDto>>> find(GroupTaskSubmissionFindDto query, Pageable pageable) {
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
    GroupTaskSubmissionEntity entity = this.groupTaskSubmissionRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("Group task submission not found");
    }

    GroupTaskSubmissionResponseDto responseDto = this.groupTaskSubmissionConverter.toGroupTaskSubmissionResponseDto(entity);
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
