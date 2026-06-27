package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.SettingSidebarLinkConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkCreateDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkFindDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkResponseDto;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkUpdateDto;
import com.eduSocialMedia.repositories.SettingSidebarLinkRepository;
import com.eduSocialMedia.repositories.entities.SettingSidebarLinkEntity;
import com.eduSocialMedia.services.SettingSidebarLinkService;

@Service
public class SettingSidebarLinkServiceImpl implements SettingSidebarLinkService {
  @Autowired
  private SettingSidebarLinkConverter settingSidebarLinkConverter;

  @Autowired
  private SettingSidebarLinkRepository settingSidebarLinkRepository;

  @Override
  public ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> create(SettingSidebarLinkCreateDto createDto) {
    if (this.settingSidebarLinkRepository.existsByPath(createDto.getPath())) {
      return ResponseDto.badRequest(List.of("Path already exists"));
    }

    SettingSidebarLinkEntity entity = this.settingSidebarLinkConverter.toSettingSidebarLinkEntity(createDto);
    entity = this.settingSidebarLinkRepository.save(entity);

    SettingSidebarLinkResponseDto responseDto = this.settingSidebarLinkConverter.toSettingSidebarLinkResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> update(Long id, SettingSidebarLinkUpdateDto updateDto) {
    SettingSidebarLinkEntity entity = this.settingSidebarLinkRepository.findById(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("SettingSidebarLink not found");
    }

    if (updateDto.getPath() != null) {
      SettingSidebarLinkEntity existing = this.settingSidebarLinkRepository.findByPath(updateDto.getPath()).orElse(null);
      if (existing != null && !existing.getId().equals(id)) {
        return ResponseDto.badRequest(List.of("Path already exists"));
      }
    }

    this.settingSidebarLinkConverter.copyToSettingSidebarLinkEntity(updateDto, entity);
    entity = this.settingSidebarLinkRepository.save(entity);

    SettingSidebarLinkResponseDto responseDto = this.settingSidebarLinkConverter.toSettingSidebarLinkResponseDto(entity);
    return ResponseDto.success(responseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    SettingSidebarLinkEntity entity = this.settingSidebarLinkRepository.findById(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("SettingSidebarLink not found");
    }

    this.settingSidebarLinkRepository.delete(entity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<SettingSidebarLinkResponseDto>>> find(SettingSidebarLinkFindDto query, Pageable pageable) {
    Specification<SettingSidebarLinkEntity> spec = this.settingSidebarLinkRepository.hasCriteria(query);
    Page<SettingSidebarLinkEntity> pageResult = this.settingSidebarLinkRepository.findAll(spec, pageable);

    int page = pageResult.getNumber();
    int size = pageResult.getSize();
    int totalPages = pageResult.getTotalPages();
    List<SettingSidebarLinkEntity> items = pageResult.getContent();

    List<SettingSidebarLinkResponseDto> dtos = items.stream()
        .map(item -> this.settingSidebarLinkConverter.toSettingSidebarLinkResponseDto(item))
        .toList();

    ResponseSpecification<SettingSidebarLinkResponseDto> responseSpec = ResponseSpecification
        .<SettingSidebarLinkResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(dtos)
        .build();

    return ResponseDto.success(responseSpec);
  }

  @Override
  public ResponseEntity<ResponseDto<SettingSidebarLinkResponseDto>> findById(Long id) {
    SettingSidebarLinkEntity entity = this.settingSidebarLinkRepository.findById(id).orElse(null);
    if (entity == null) {
      return ResponseDto.notFound("SettingSidebarLink not found");
    }

    SettingSidebarLinkResponseDto responseDto = this.settingSidebarLinkConverter.toSettingSidebarLinkResponseDto(entity);
    return ResponseDto.success(responseDto);
  }
}
