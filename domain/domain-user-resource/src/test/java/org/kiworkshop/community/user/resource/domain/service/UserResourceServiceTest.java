package org.kiworkshop.community.user.resource.domain.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.user.resource.domain.model.UserResource;
import org.kiworkshop.community.user.resource.domain.model.UserResourceFixture;
import org.kiworkshop.community.user.resource.domain.model.UserResourceRepository;
import org.kiworkshop.community.user.resource.dto.UserResourceResponseDto;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserResourceServiceTest {
  private UserResourceService userResourceService;

  private @Mock UserResourceRepository userResourceRepository;

  @BeforeEach
  void setUp() {
    userResourceService = new UserResourceService(userResourceRepository);
  }

  @Test
  void me_ValidInput_ValidOutput() {
    // given
    UserResource userResource = UserResourceFixture.get();
    given(userResourceRepository.findByUsername(any())).willReturn(Optional.of(UserResourceFixture.get()));

    // when
    UserResourceResponseDto userResourceResponseDto = userResourceService.me("username");

    // then
    then(userResourceResponseDto.getNickname()).isEqualTo(userResource.getNickname());
    then(userResourceResponseDto.getContactEmail()).isEqualTo(userResource.getContactEmail());
    then(userResourceResponseDto.getUsername()).isEqualTo(userResource.getUsername());
  }
}