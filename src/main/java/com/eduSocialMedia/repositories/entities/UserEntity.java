package com.eduSocialMedia.repositories.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity {
  @Column(name = "full_name", nullable = false, length = 100)
  private String fullName;

  @Column(name = "slug", nullable = false, unique = true, length = 100)
  private String slug;

  @Column(name = "user_name", nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(columnDefinition = "TEXT")
  private String avatar;

  @Column(name = "cover_photo", columnDefinition = "TEXT")
  private String coverPhoto;

  @Column(columnDefinition = "TEXT")
  private String bio;

  @Column(name = "is_active")
  @Builder.Default
  private Boolean isActive = true;

  @Column(name = "is_deleted")
  @Builder.Default
  private Boolean isDeleted = false;

  @OneToMany(mappedBy = "user")
  private List<UserArticleEntity> userArticles;

  @OneToMany(mappedBy = "user")
  private List<GroupTaskSubmissionEntity> groupTaskSubmissions;

  @OneToMany(mappedBy = "user")
  private List<ChatMessageEntity> chatMessages;
}
