package org.kiworkshop.community.mother.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AboutResponseDtoFixture {

  public static AboutResponseDto get() {
    return AboutResponseDto.builder()
        .title("title")
        .content("content")
        .build();
  }
}
