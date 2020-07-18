package org.kiworkshop.community.content.jgraphy.api.dto;

import org.kiworkshop.community.content.jgraphy.domain.JgraphyPost;
import org.kiworkshop.community.content.jgraphy.domain.JgraphyPostTest;

public class JgraphyPostResponseDtoTest {
  public static JgraphyPostResponseDto getJgraphyPostResponseDtoFixture() throws Exception {
    return getJgraphyPostResponseDtoFixture(1L);
  }

  public static JgraphyPostResponseDto getJgraphyPostResponseDtoFixture(Long id) {
    JgraphyPost jgraphyPost = JgraphyPostTest.getJgraphyPostFixture(id);
    return JgraphyPostResponseDto.builder()
        .id(jgraphyPost.getId())
        .title(jgraphyPost.getTitle())
        .content(jgraphyPost.getContent())
        .createdAt(jgraphyPost.getCreatedAt())
        .updatedAt(jgraphyPost.getUpdatedAt())
        .build();
  }
}