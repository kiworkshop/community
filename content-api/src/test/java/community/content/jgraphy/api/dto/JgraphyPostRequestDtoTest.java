package community.content.jgraphy.api.dto;

import org.springframework.test.util.ReflectionTestUtils;

public class JgraphyPostRequestDtoTest {
  public static JgraphyPostRequestDto getJgraphyPostRequestDtoFixture() {
    return getJgraphyPostRequestDtoFixture("title", "content");
  }

  public static JgraphyPostRequestDto getJgraphyPostRequestDtoFixture(String title, String content) {
    JgraphyPostRequestDto jgraphyPostRequestDto = new JgraphyPostRequestDto();
    ReflectionTestUtils.setField(jgraphyPostRequestDto, "title", title);
    ReflectionTestUtils.setField(jgraphyPostRequestDto, "content", content);
    return jgraphyPostRequestDto;
  }
}