package com.eduSocialMedia.dtos.auth;

import jakarta.validation.constraints.NotBlank;
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
public class AuthLoginDto {
  @NotBlank(message = "User name is not blank")
  private String userName;

  @NotBlank(message = "Password is not blank")
  private String password;
}
