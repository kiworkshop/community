package org.kiworkshop.community.auth.api.dto;

import org.kiworkshop.community.auth.dto.SocialResourceResponseDto;

public class SocialResourceResponseDtoTest {
  public static SocialResourceResponseDto getSocialResourceResponseDtoFixture() {
    return SocialResourceResponseDto.builder()
        .socialId("socialId")
        .contactEmail("foo@bar.com")
        .build();
  }
}
