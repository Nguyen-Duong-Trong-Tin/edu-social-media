package com.eduSocialMedia.controllers.client;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduSocialMedia.annotations.Auth;
import com.eduSocialMedia.annotations.CurrentUser;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.groupTaskSubmissions.ClientGroupTaskSubmissionCreateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.ClientGroupTaskSubmissionUpdateDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionFindDto;
import com.eduSocialMedia.dtos.groupTaskSubmissions.GroupTaskSubmissionResponseDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.repositories.entities.UserEntity;
import com.eduSocialMedia.services.client.ClientGroupTaskSubmissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/group-task-submissions")
public class ClientGroupTaskSubmissionController {
  private final ClientGroupTaskSubmissionService groupTaskSubmissionService;

  ClientGroupTaskSubmissionController(ClientGroupTaskSubmissionService groupTaskSubmissionService) {
    this.groupTaskSubmissionService = groupTaskSubmissionService;
  }

  @PostMapping
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> create(
      @Valid @RequestBody ClientGroupTaskSubmissionCreateDto createDto, @CurrentUser UserEntity me) {
    return this.groupTaskSubmissionService.create(createDto, me);
  }

  @PatchMapping("/{id}")
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody ClientGroupTaskSubmissionUpdateDto updateDto, @CurrentUser UserEntity me) {
    return this.groupTaskSubmissionService.update(id, updateDto, me);
  }

  @DeleteMapping("/{id}")
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id, @CurrentUser UserEntity me) {
    return this.groupTaskSubmissionService.delete(id, me);
  }

  @GetMapping
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<ResponseSpecification<GroupTaskSubmissionResponseDto>>> find(
      GroupTaskSubmissionFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, @CurrentUser UserEntity me) {
    return this.groupTaskSubmissionService.find(query, pageable, me);
  }

  @GetMapping("/{id}")
  @Auth({ AuthTypeEnum.USER })
  public ResponseEntity<ResponseDto<GroupTaskSubmissionResponseDto>> findById(@PathVariable Long id,
      @CurrentUser UserEntity me) {
    return this.groupTaskSubmissionService.findById(id, me);
  }
}
