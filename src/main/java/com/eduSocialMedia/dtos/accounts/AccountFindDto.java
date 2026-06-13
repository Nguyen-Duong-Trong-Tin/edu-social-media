package com.eduSocialMedia.dtos.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountFindDto {
  private String fullName;
  private String userName;
  private Boolean isActive;
  private Long roleId;
  private String roleName;
}
