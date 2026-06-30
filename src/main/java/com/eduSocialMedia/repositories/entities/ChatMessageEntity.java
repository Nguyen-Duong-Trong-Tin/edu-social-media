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
@Table(name = "chat_messages")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageEntity extends BaseEntity {
  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(columnDefinition = "TEXT")
  private String images;

  @Column(columnDefinition = "TEXT")
  private String videos;

  @Column(columnDefinition = "TEXT")
  private String materials;

  @Column(name = "is_deleted")
  @Builder.Default
  private Boolean isDeleted = false;

  @ManyToOne
  @JoinColumn(nullable = false)
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "chat_room_id", nullable = false)
  private ChatRoomEntity chatRoom;
}
