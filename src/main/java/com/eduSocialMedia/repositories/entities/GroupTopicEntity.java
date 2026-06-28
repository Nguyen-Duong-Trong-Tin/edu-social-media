package com.eduSocialMedia.repositories.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "group_topics")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupTopicEntity extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column(name = "slug", nullable = false, unique = true)
  private String slug;

  @Column(columnDefinition = "TEXT")
  private String description;

  @OneToMany(mappedBy = "groupTopic")
  private List<GroupEntity> groups;
}
