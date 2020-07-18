package org.kiworkshop.community.auth.api.dto;

public class SocialResourceResponseDtoTest {
  public static SocialResourceResponseDto getSocialResourceResponseDtoFixture() {
    return SocialResourceResponseDto.builder()
        .socialId("socialId")
        .contactEmail("foo@bar.com")
        .build();
  }
}
