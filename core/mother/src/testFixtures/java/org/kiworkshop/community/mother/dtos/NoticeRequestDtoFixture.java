package org.kiworkshop.community.mother.dtos;

import org.springframework.test.util.ReflectionTestUtils;

public class NoticeRequestDtoFixture {
  public static NoticeRequestDto get() {
    return get("title", "content");
  }

  public static NoticeRequestDto get(String title, String content) {
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();

    ReflectionTestUtils.setField(noticeRequestDto, "title", title);
    ReflectionTestUtils.setField(noticeRequestDto, "content", content);

    return noticeRequestDto;
  }
}
