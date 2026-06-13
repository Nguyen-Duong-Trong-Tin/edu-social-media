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
@Table(name = "accounts")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountEntity extends BaseEntity {
  @Column(nullable = false, length = 100)
  private String fullName;

  @Column(nullable = false, unique = true, length = 100)
  private String userName;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(length = 255)
  private String avatar;

  @Column
  @Builder.Default
  private Boolean isActive = true;

  @Column
  @Builder.Default
  private Boolean isDeleted = false;

  @ManyToOne
  @JoinColumn(name = "roleId", nullable = false)
  private RoleEntity role;
}
