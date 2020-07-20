package org.kiworkshop.community.comment.dto;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponseDtoFixture {
  public static CommentResponseDto get(Long id) {
    return CommentResponseDto.builder()
        .id(id)
        .username("username")
        .content("content")
        .ordinal(0)
        .active(true)
        .createdAt(ZonedDateTime.now())
        .build();
  }
}