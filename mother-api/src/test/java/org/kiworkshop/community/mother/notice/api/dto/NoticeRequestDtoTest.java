package org.kiworkshop.community.mother.notice.api.dto;

import org.springframework.test.util.ReflectionTestUtils;

public class NoticeRequestDtoTest {
  public static NoticeRequestDto getNoticeRequestDtoFixture() throws Exception {
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();

    ReflectionTestUtils.setField(noticeRequestDto, "title", "title");
    ReflectionTestUtils.setField(noticeRequestDto, "content", "content");

    return noticeRequestDto;
  }

  public static NoticeRequestDto getNoticeRequestDtoFixture(String title, String content) throws Exception {
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();

    ReflectionTestUtils.setField(noticeRequestDto, "title", title);
    ReflectionTestUtils.setField(noticeRequestDto, "content", content);

    return noticeRequestDto;
  }
}

