package community.content.jgraphy.api.dto;

import community.common.util.MyReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

class JgraphyTagRequestDtoTest {
  public static JgraphyTagRequestDto getJgraphyTagRequestDtoFixture() throws Exception{
    return getJgraphyTagRequestDtoFixture("tag");
  }

  public static JgraphyTagRequestDto getJgraphyTagRequestDtoFixture(String tag) throws Exception {
    JgraphyTagRequestDto jgraphyTagRequestDto = new JgraphyTagRequestDto();
    MyReflectionUtils.setField(jgraphyTagRequestDto, "tag", tag);
    return jgraphyTagRequestDto;
  }


}