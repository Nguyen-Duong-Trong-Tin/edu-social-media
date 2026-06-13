package com.eduSocialMedia.dtos.accounts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AccountCreateDto {
  @NotBlank(message = "Full name is not blank")
  @Size(max = 100, message = "Full name maximum length is 100 characters")
  private String fullName;

  @NotBlank(message = "User name is not blank")
  @Size(max = 100, message = "User name maximum length is 100 characters")
  private String userName;

  @NotBlank(message = "Password is not blank")
  @Size(max = 255, message = "Password maximum length is 255 characters")
  private String password;

  @NotNull(message = "Location id is required")
  private Long roleId;
}
