package org.kiworkshop.community.user.resource.domain.service;

import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.user.resource.domain.exception.UserResourceNotFoundException;
import org.kiworkshop.community.user.resource.domain.model.UserResource;
import org.kiworkshop.community.user.resource.domain.model.UserResourceRepository;
import org.kiworkshop.community.user.resource.dto.UserResourceResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserResourceService {
  private final UserResourceRepository userResourceRepository;

  public UserResourceResponseDto me(String username) {
    UserResource userResource = userResourceRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserResourceNotFoundException(username));
    return UserResourceConverter.toResponseDto(userResource);
  }
}
