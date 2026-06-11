package com.eduSocialMedia.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduSocialMedia.converters.PermissionConverter;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.permissions.PermissionCreateDto;
import com.eduSocialMedia.dtos.permissions.PermissionFindDto;
import com.eduSocialMedia.dtos.permissions.PermissionResponseDto;
import com.eduSocialMedia.dtos.permissions.PermissionUpdateDto;
import com.eduSocialMedia.repositories.PermissionRepository;
import com.eduSocialMedia.repositories.entities.PermissionEntity;
import com.eduSocialMedia.services.PermissionService;
import com.eduSocialMedia.utils.ValidationUtil;

@Service
public class PermissionServiceImpl implements PermissionService {
  @Autowired
  private PermissionConverter permissionConverter;

  @Autowired
  private PermissionRepository permissionRepository;

  @Override
  public ResponseEntity<ResponseDto<PermissionResponseDto>> create(PermissionCreateDto permissionCreateDto) {
    PermissionEntity permissionEntity = this.permissionConverter.toPermissionEntity(permissionCreateDto);
    permissionEntity.setCode(this.buildCode(permissionEntity.getName()));

    permissionEntity = this.permissionRepository.save(permissionEntity);

    PermissionResponseDto permissionResponseDto = this.permissionConverter.toPermissionResponseDto(permissionEntity);

    return ResponseDto.created(permissionResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<PermissionResponseDto>> update(Long id, PermissionUpdateDto permissionUpdateDto) {
    PermissionEntity permissionEntity = this.permissionRepository.findById(id).orElse(null);

    if (permissionEntity == null) {
      return ResponseDto.notFound("Permission not found");
    }

    this.permissionConverter.copyToPermissionEntity(permissionUpdateDto, permissionEntity);

    if (!ValidationUtil.isNullOrEmpty(permissionUpdateDto.getName())) {
      permissionEntity.setCode(this.buildCode(permissionUpdateDto.getName()));
    }

    permissionEntity = this.permissionRepository.save(permissionEntity);

    PermissionResponseDto permissionResponseDto = this.permissionConverter.toPermissionResponseDto(permissionEntity);
    return ResponseDto.success(permissionResponseDto);
  }

  @Override
  public ResponseEntity<ResponseDto<Object>> delete(Long id) {
    PermissionEntity permissionEntity = this.permissionRepository.findById(id).orElse(null);

    if (permissionEntity == null) {
      return ResponseDto.notFound("Permission not found");
    }

    this.permissionRepository.delete(permissionEntity);
    return ResponseDto.success(null);
  }

  @Override
  public ResponseEntity<ResponseDto<ResponseSpecification<PermissionResponseDto>>> find(PermissionFindDto query,
      Pageable pageable) {
    Specification<PermissionEntity> permissionSpec = this.permissionRepository.hasCriteria(query);

    Page<PermissionEntity> permissionPage = this.permissionRepository.findAll(permissionSpec, pageable);

    int page = permissionPage.getNumber();
    int size = permissionPage.getSize();
    int totalPages = permissionPage.getTotalPages();
    List<PermissionEntity> items = permissionPage.getContent();

    List<PermissionResponseDto> permissionResponseDtos = items.stream()
        .map(item -> this.permissionConverter.toPermissionResponseDto(item)).toList();
    ResponseSpecification<PermissionResponseDto> permissionResponseSpecification = ResponseSpecification
        .<PermissionResponseDto>builder()
        .page(page)
        .size(size)
        .totalPages(totalPages)
        .items(permissionResponseDtos)
        .build();
    return ResponseDto.success(permissionResponseSpecification);
  }

  @Override
  public ResponseEntity<ResponseDto<PermissionResponseDto>> findById(Long id) {
    PermissionEntity permissionEntity = this.permissionRepository.findById(id).orElse(null);
    if (permissionEntity == null) {
      return ResponseDto.notFound("Permission not found");
    }

    PermissionResponseDto permissionResponseDto = this.permissionConverter.toPermissionResponseDto(permissionEntity);
    return ResponseDto.success(permissionResponseDto);
  }

  private String buildCode(String name) {
    String code = name == null ? null : name.trim().toUpperCase().replaceAll("[^A-Z0-9]+", "_")
        .replaceAll("^_|_$", "");
    return ValidationUtil.isNullOrEmpty(code) ? name : code;
  }
}