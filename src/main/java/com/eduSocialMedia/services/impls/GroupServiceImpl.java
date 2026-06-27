package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.GroupConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groups.GroupCreateDto;
import com.eduSocialMedia.dtos.groups.GroupFindDto;
import com.eduSocialMedia.dtos.groups.GroupResponseDto;
import com.eduSocialMedia.dtos.groups.GroupUpdateDto;
import com.eduSocialMedia.repositories.GroupRepository;
import com.eduSocialMedia.repositories.GroupTopicRepository;
import com.eduSocialMedia.repositories.entities.GroupEntity;
import com.eduSocialMedia.repositories.entities.GroupTopicEntity;
import com.eduSocialMedia.services.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
  @Autowired
  private GroupConverter groupConverter;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private GroupTopicRepository groupTopicRepository;

  @Override
  public ResponseEntity<ResponseDto<GroupResponseDto>> create(GroupCreateDto groupCreateDto) {
    GroupEntity nameExists = this.groupRepository.findByNameAndIsDeletedFalse(groupCreateDto.getName()).orElse(null);
    if (nameExists != null) {
      return ResponseDto.badRequest(List.of("Group name already exists"));
    }

    GroupTopicEntity groupTopic = this.groupTopicRepository.findById(groupCreateDto.getGroupTopicId()).orElse(null);
    if (groupTopic == null) {
      return ResponseDto.notFound("Group topic not found");
    }

    GroupEntity groupEntity = this.groupConverter.toGroupEntity(groupCreateDto);
    groupEntity.setGroupTopic(groupTopic);
    groupEntity.setSlug(generateUniqueSlug(groupCreateDto.getName()));

    groupEntity = this.groupRepository.save(groupEntity);

    GroupResponseDto groupResponseDto = this.groupConverter.toGroupResponseDto(groupEntity);
    return ResponseDto.created(groupResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupResponseDto>> update(Long id, GroupUpdateDto groupUpdateDto) {
    GroupEntity groupEntity = this.groupRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupEntity == null) {
      return ResponseDto.notFound("Group not found");
    }

    if (groupUpdateDto.getName() != null && !groupUpdateDto.getName().trim().isEmpty()
        && !groupUpdateDto.getName().equals(groupEntity.getName())) {
      GroupEntity nameExists = this.groupRepository.findByNameAndIsDeletedFalse(groupUpdateDto.getName()).orElse(null);
      if (nameExists != null) {
        return ResponseDto.badRequest(List.of("Group name already exists"));
      }
      groupEntity.setSlug(generateUniqueSlug(groupUpdateDto.getName()));
    }

    if (groupUpdateDto.getGroupTopicId() != null) {
      GroupTopicEntity groupTopic = this.groupTopicRepository.findById(groupUpdateDto.getGroupTopicId()).orElse(null);
      if (groupTopic == null) {
        return ResponseDto.notFound("Group topic not found");
      }
      groupEntity.setGroupTopic(groupTopic);
    }

    this.groupConverter.copyToGroupEntity(groupUpdateDto, groupEntity);

    groupEntity = this.groupRepository.save(groupEntity);

    GroupResponseDto groupResponseDto = this.groupConverter.toGroupResponseDto(groupEntity);
    return ResponseDto.success(groupResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    GroupEntity groupEntity = this.groupRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupEntity == null) {
      return ResponseDto.notFound("Group not found");
    }

    groupEntity.setIsDeleted(true);
    this.groupRepository.save(groupEntity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupResponseDto>>> find(GroupFindDto query, Pageable pageable) {
    Specification<GroupEntity> spec = this.groupRepository.hasCriteria(query);
    Page<GroupEntity> groupPage = this.groupRepository.findAll(spec, pageable);

    int page = groupPage.getNumber();
    int size = groupPage.getSize();
    int totalPages = groupPage.getTotalPages();
    List<GroupEntity> items = groupPage.getContent();

    List<GroupResponseDto> responseDtos = items.stream()
        .map(item -> this.groupConverter.toGroupResponseDto(item)).toList();

    ResponseSpecification<GroupResponseDto> specResult = ResponseSpecification
        .<GroupResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(responseDtos)
        .build();

    return ResponseDto.success(specResult);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupResponseDto>> findById(Long id) {
    GroupEntity groupEntity = this.groupRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    if (groupEntity == null) {
      return ResponseDto.notFound("Group not found");
    }

    GroupResponseDto groupResponseDto = this.groupConverter.toGroupResponseDto(groupEntity);
    return ResponseDto.success(groupResponseDto);
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
    while (this.groupRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }
}
