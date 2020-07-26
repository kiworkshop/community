package org.kiworkshop.community.user.resource.dto;

public class UserResourceResponseDtoFixture {
  public static UserResourceResponseDto get() {
    return UserResourceResponseDto.builder()
        .username("username")
        .nickname("nickname")
        .contactEmail("foo@bar.com")
        .build();
  }
}
