package org.kiworkshop.community.mother.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeResponseDtoFixture {

  public static NoticeResponseDto get() {
    return get(1L);
  }

  public static NoticeResponseDto get(Long id) {
    return NoticeResponseDto.builder()
        .id(id)
        .title("title")
        .content("content")
        .build();
  }
}
