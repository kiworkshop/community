package org.kiworkshop.community.mother.dtos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
