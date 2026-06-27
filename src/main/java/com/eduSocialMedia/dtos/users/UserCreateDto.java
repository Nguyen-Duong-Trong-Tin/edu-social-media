package com.eduSocialMedia.dtos.users;

import jakarta.validation.constraints.NotBlank;
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
public class UserCreateDto {
  @NotBlank(message = "Full name is not blank")
  @Size(max = 100, message = "Full name maximum length is 100 characters")
  private String fullName;

  @NotBlank(message = "Email is not blank")
  @Size(max = 255, message = "Email maximum length is 255 characters")
  private String email;

  @NotBlank(message = "Password is not blank")
  private String password;

  private String bio;
}
