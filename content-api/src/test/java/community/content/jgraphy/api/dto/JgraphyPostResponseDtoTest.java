package community.content.jgraphy.api.dto;

import community.content.jgraphy.domain.JgraphyPost;
import org.springframework.test.util.ReflectionTestUtils;

import static community.content.jgraphy.domain.JgraphyPostTest.getJgraphyPostFixture;
import static org.assertj.core.api.BDDAssertions.then;

public class JgraphyPostResponseDtoTest {
  public static JgraphyPostResponseDto getJgraphyPostResponseDtoFixture() throws Exception {
    return getJgraphyPostResponseDtoFixture(1L);
  }

  public static JgraphyPostResponseDto getJgraphyPostResponseDtoFixture(Long id) {
    JgraphyPost jgraphyPost = getJgraphyPostFixture(id);
    return JgraphyPostResponseDto.builder()
        .id(jgraphyPost.getId())
        .title(jgraphyPost.getTitle())
        .content(jgraphyPost.getContent())
        .createdAt(jgraphyPost.getCreatedAt())
        .updatedAt(jgraphyPost.getUpdatedAt())
        .build();
  }
}