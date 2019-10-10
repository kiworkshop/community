package community.content.jgraphy.api.dto;

import community.common.util.MyReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JgraphyPostRequestDtoTest {
  public static JgraphyPostRequestDto getJgraphyPostRequestDtoFixture() throws Exception {
    return getJgraphyPostRequestDtoFixture("title", "content");
  }

  private static JgraphyPostRequestDto getJgraphyPostRequestDtoFixture(String title, String content) throws Exception {
    JgraphyPostRequestDto jgraphyPostRequestDto = new JgraphyPostRequestDto();
    MyReflectionUtils.setField(jgraphyPostRequestDto, "title", title);
    MyReflectionUtils.setField(jgraphyPostRequestDto, "content", content);
    return jgraphyPostRequestDto;
  }

}