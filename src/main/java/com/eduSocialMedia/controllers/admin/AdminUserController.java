package com.eduSocialMedia.controllers.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eduSocialMedia.annotations.Auth;
import com.eduSocialMedia.dtos.ResponseDto;
import com.eduSocialMedia.dtos.ResponseSpecification;
import com.eduSocialMedia.dtos.users.UserCreateDto;
import com.eduSocialMedia.dtos.users.UserFindDto;
import com.eduSocialMedia.dtos.users.UserResponseDto;
import com.eduSocialMedia.dtos.users.UserUpdateDto;
import com.eduSocialMedia.dtos.users.UserUploadDto;
import com.eduSocialMedia.enums.AuthTypeEnum;
import com.eduSocialMedia.services.admin.AdminUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/admin/users")
public class AdminUserController {
  private final AdminUserService userService;

  AdminUserController(AdminUserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<UserResponseDto>> create(@Valid @RequestBody UserCreateDto userCreateDto) {
    return this.userService.create(userCreateDto);
  }

  @PostMapping("/upload")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<UserResponseDto>> upload(@Valid @ModelAttribute UserUploadDto userUploadDto, @RequestParam MultipartFile file) {
    return this.userService.upload(userUploadDto, file);
  }

  @PatchMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<UserResponseDto>> update(@PathVariable Long id,
      @Valid @RequestBody UserUpdateDto userUpdateDto) {
    return this.userService.update(id, userUpdateDto);
  }

  @DeleteMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Long id) {
    return this.userService.delete(id);
  }

  @GetMapping
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<ResponseSpecification<UserResponseDto>>> find(UserFindDto query,
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
    return this.userService.find(query, pageable);
  }

  @GetMapping("/{id}")
  @Auth({ AuthTypeEnum.ACCOUNT })
  public ResponseEntity<ResponseDto<UserResponseDto>> findById(@PathVariable Long id) {
    return this.userService.findById(id);
  }
}
