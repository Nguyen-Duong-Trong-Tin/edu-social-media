package com.eduSocialMedia.dtos.userArticleFiles;

import com.eduSocialMedia.dtos.BaseResponseDto;

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
public class UserArticleFileResponseDto extends BaseResponseDto {
  private Long id;
  private String type;
  private String path;
  private Long userArticleId;
}
