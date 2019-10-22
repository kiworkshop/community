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
    JgraphyPostResponseDto jgraphyPostResponseDto = new JgraphyPostResponseDto();
    ReflectionTestUtils.setField(jgraphyPost, "id" , jgraphyPost.getId());
    ReflectionTestUtils.setField(jgraphyPost, "title", jgraphyPost.getTitle());
    ReflectionTestUtils.setField(jgraphyPost, "content", jgraphyPost.getContent());
    return jgraphyPostResponseDto;
  }
}