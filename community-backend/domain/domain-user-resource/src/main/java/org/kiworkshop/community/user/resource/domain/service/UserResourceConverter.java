package org.kiworkshop.community.user.resource.domain.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.user.resource.domain.model.UserResource;
import org.kiworkshop.community.user.resource.dto.UserResourceResponseDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResourceConverter {
  public static UserResourceResponseDto toResponseDto(UserResource userResource) {
    return UserResourceResponseDto.builder()
        .username(userResource.getUsername())
        .nickname(userResource.getNickname())
        .contactEmail(userResource.getContactEmail())
        .build();
  }
}
