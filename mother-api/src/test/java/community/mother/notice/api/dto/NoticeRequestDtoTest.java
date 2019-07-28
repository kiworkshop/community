package community.mother.notice.api.dto;

import community.common.util.MyReflectionUtils;

public class NoticeRequestDtoTest {
  public static NoticeRequestDto getNoticeRequestDtoFixture() throws Exception {
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();
    MyReflectionUtils.setField(noticeRequestDto, "title", "title");
    MyReflectionUtils.setField(noticeRequestDto, "content", "content");

    return noticeRequestDto;
  }
}

