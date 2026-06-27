package com.eduSocialMedia.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "setting_sidebar_links")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SettingSidebarLinkEntity extends BaseEntity {
  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, unique = true, length = 50)
  private String path;

  @Column(nullable = false, length = 50)
  private String icon;

  @Column(name = "is_exact")
  @Builder.Default
  private Boolean isExact = false;

  @Column(name = "sort_order")
  private Integer sortOrder;
}
