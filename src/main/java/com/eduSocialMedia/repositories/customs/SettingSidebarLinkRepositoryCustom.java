package com.eduSocialMedia.repositories.customs;

import org.springframework.data.jpa.domain.Specification;
import com.eduSocialMedia.dtos.settingSidebarLinks.SettingSidebarLinkFindDto;
import com.eduSocialMedia.repositories.entities.SettingSidebarLinkEntity;

public interface SettingSidebarLinkRepositoryCustom {
  Specification<SettingSidebarLinkEntity> hasCriteria(SettingSidebarLinkFindDto query);
}
