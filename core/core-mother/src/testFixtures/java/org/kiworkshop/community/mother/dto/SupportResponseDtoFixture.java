package org.kiworkshop.community.mother.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportResponseDtoFixture {

  public static SupportResponseDto get() {
    return SupportResponseDto.builder()
        .title("title")
        .content("content")
        .build();
  }
}
