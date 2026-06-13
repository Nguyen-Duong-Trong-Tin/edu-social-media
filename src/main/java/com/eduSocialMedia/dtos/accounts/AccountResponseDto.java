package com.eduSocialMedia.dtos.accounts;

import com.eduSocialMedia.dtos.BaseResponseDto;
import com.eduSocialMedia.dtos.roles.RoleResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountResponseDto extends BaseResponseDto {
  private String fullName;
  private String userName;
  private String avatar;
  private Boolean isActive;
  private RoleResponseDto role;
}
