package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.GroupTopicConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicCreateDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicFindDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicResponseDto;
import com.eduSocialMedia.dtos.groupTopics.GroupTopicUpdateDto;
import com.eduSocialMedia.repositories.GroupTopicRepository;
import com.eduSocialMedia.repositories.entities.GroupTopicEntity;
import com.eduSocialMedia.services.GroupTopicService;

@Service
public class GroupTopicServiceImpl implements GroupTopicService {
  @Autowired
  private GroupTopicConverter groupTopicConverter;

  @Autowired
  private GroupTopicRepository groupTopicRepository;

  @Override
  public ResponseEntity<ResponseDto<GroupTopicResponseDto>> create(GroupTopicCreateDto groupTopicCreateDto) {
    GroupTopicEntity topicExists = this.groupTopicRepository.findByName(groupTopicCreateDto.getName()).orElse(null);
    if (topicExists != null) {
      return ResponseDto.badRequest(List.of("Group topic name already exists"));
    }

    GroupTopicEntity groupTopicEntity = this.groupTopicConverter.toGroupTopicEntity(groupTopicCreateDto);
    groupTopicEntity.setSlug(generateUniqueSlug(groupTopicCreateDto.getName()));

    groupTopicEntity = this.groupTopicRepository.save(groupTopicEntity);

    GroupTopicResponseDto groupTopicResponseDto = this.groupTopicConverter.toGroupTopicResponseDto(groupTopicEntity);
    return ResponseDto.created(groupTopicResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupTopicResponseDto>> update(Long id, GroupTopicUpdateDto groupTopicUpdateDto) {
    GroupTopicEntity groupTopicEntity = this.groupTopicRepository.findById(id).orElse(null);
    if (groupTopicEntity == null) {
      return ResponseDto.notFound("Group topic not found");
    }

    if (groupTopicUpdateDto.getName() != null && !groupTopicUpdateDto.getName().trim().isEmpty()
        && !groupTopicUpdateDto.getName().equals(groupTopicEntity.getName())) {
      GroupTopicEntity nameExists = this.groupTopicRepository.findByName(groupTopicUpdateDto.getName()).orElse(null);
      if (nameExists != null) {
        return ResponseDto.badRequest(List.of("Group topic name already exists"));
      }
      groupTopicEntity.setSlug(generateUniqueSlug(groupTopicUpdateDto.getName()));
    }

    this.groupTopicConverter.copyToGroupTopicEntity(groupTopicUpdateDto, groupTopicEntity);

    groupTopicEntity = this.groupTopicRepository.save(groupTopicEntity);

    GroupTopicResponseDto groupTopicResponseDto = this.groupTopicConverter.toGroupTopicResponseDto(groupTopicEntity);
    return ResponseDto.success(groupTopicResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    GroupTopicEntity groupTopicEntity = this.groupTopicRepository.findById(id).orElse(null);
    if (groupTopicEntity == null) {
      return ResponseDto.notFound("Group topic not found");
    }

    this.groupTopicRepository.delete(groupTopicEntity);

    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTopicResponseDto>>> find(GroupTopicFindDto query, Pageable pageable) {
    Specification<GroupTopicEntity> spec = this.groupTopicRepository.hasCriteria(query);
    Page<GroupTopicEntity> topicPage = this.groupTopicRepository.findAll(spec, pageable);

    int page = topicPage.getNumber();
    int size = topicPage.getSize();
    int totalPages = topicPage.getTotalPages();
    List<GroupTopicEntity> items = topicPage.getContent();

    List<GroupTopicResponseDto> responseDtos = items.stream()
        .map(item -> this.groupTopicConverter.toGroupTopicResponseDto(item)).toList();

    ResponseSpecification<GroupTopicResponseDto> specResult = ResponseSpecification
        .<GroupTopicResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(responseDtos)
        .build();

    return ResponseDto.success(specResult);
  }

  @Override
  public ResponseEntity<ResponseDto<GroupTopicResponseDto>> findById(Long id) {
    GroupTopicEntity groupTopicEntity = this.groupTopicRepository.findById(id).orElse(null);
    if (groupTopicEntity == null) {
      return ResponseDto.notFound("Group topic not found");
    }

    GroupTopicResponseDto groupTopicResponseDto = this.groupTopicConverter.toGroupTopicResponseDto(groupTopicEntity);
    return ResponseDto.success(groupTopicResponseDto);
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
    while (this.groupTopicRepository.findBySlug(slug).isPresent()) {
      slug = baseSlug + "-" + counter;
      counter++;
    }
    return slug;
  }
}
