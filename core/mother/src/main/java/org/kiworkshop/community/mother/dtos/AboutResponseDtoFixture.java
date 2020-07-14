package org.kiworkshop.community.mother.dtos;

public class AboutResponseDtoFixture {
  public static AboutResponseDto get() {
    return AboutResponseDto.builder()
        .title("title")
        .content("content")
        .build();
  }
}
