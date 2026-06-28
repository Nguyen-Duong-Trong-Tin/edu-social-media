package com.eduSocialMedia.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "group_articles")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupArticleEntity extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column(name = "slug", nullable = false, unique = true)
  private String slug;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "TEXT")
  private String images;

  @Column(columnDefinition = "TEXT")
  private String videos;

  @Column(name = "is_active")
  @Builder.Default
  private Boolean isActive = true;

  @Column(name = "is_deleted")
  @Builder.Default
  private Boolean isDeleted = false;

  @ManyToOne
  @JoinColumn(nullable = false)
  private GroupEntity group;
}
