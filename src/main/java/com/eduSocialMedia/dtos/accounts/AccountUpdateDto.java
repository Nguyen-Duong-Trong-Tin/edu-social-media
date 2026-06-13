package com.eduSocialMedia.dtos.accounts;

import jakarta.validation.constraints.Size;
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
public class AccountUpdateDto {
  @Size(max = 100, message = "Full name maximum length is 100 characters")
  private String fullName;

  private Long roleId;
}
