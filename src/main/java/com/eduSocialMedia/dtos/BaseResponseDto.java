package com.eduSocialMedia.dtos;

import java.time.LocalDateTime;

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
public class BaseResponseDto {
  private Long id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
