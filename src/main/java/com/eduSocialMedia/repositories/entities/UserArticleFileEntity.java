package com.eduSocialMedia.repositories.entities;

import com.eduSocialMedia.enums.UserArticleFileTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_articles")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserArticleFileEntity extends BaseEntity {
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserArticleFileTypeEnum type;

  @Column(columnDefinition = "TEXT")
  private String path;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_article_id", nullable = false)
  private UserArticleEntity userArticle;
}
