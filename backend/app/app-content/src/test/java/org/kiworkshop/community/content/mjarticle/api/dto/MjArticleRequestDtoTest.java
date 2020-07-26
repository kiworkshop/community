package org.kiworkshop.community.content.mjarticle.api.dto;

import org.springframework.test.util.ReflectionTestUtils;

public class MjArticleRequestDtoTest {
  public static MjArticleRequestDto getMjArticleRequestDtoFixture() {
    MjArticleRequestDto mjArticleRequestDto = new MjArticleRequestDto();

    ReflectionTestUtils.setField(mjArticleRequestDto, "title", "title");
    ReflectionTestUtils.setField(mjArticleRequestDto, "content", "content");

    return mjArticleRequestDto;
  }
}

