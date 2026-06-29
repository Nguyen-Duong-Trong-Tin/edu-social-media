package com.eduSocialMedia.repositories.entities;

import com.eduSocialMedia.repositories.enums.ChatRoomTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "chat_rooms")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatRoomEntity extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column(name = "slug", nullable = false, unique = true)
  private String slug;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ChatRoomTypeEnum type;

  @Column(columnDefinition = "TEXT")
  private String avatar;

  @Column(name = "is_active")
  @Builder.Default
  private Boolean isActive = true;

  @Column(name = "is_deleted")
  @Builder.Default
  private Boolean isDeleted = false;

  @ManyToOne
  @JoinColumn
  private GroupEntity group;
}
