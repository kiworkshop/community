package org.kiworkshop.community.content.jgraphy.api.dto;

import org.springframework.test.util.ReflectionTestUtils;

class JgraphyTagRequestDtoTest {
  public static JgraphyTagRequestDto getJgraphyTagRequestDtoFixture() {
    return getJgraphyTagRequestDtoFixture("tag");
  }

  public static JgraphyTagRequestDto getJgraphyTagRequestDtoFixture(String tag) {
    JgraphyTagRequestDto jgraphyTagRequestDto = new JgraphyTagRequestDto();
    ReflectionTestUtils.setField(jgraphyTagRequestDto, "tag", tag);
    return jgraphyTagRequestDto;
  }


}