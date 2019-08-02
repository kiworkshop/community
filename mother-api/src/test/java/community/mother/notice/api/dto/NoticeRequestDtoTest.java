package community.mother.notice.api.dto;

import community.common.util.MyReflectionUtils;
import java.lang.reflect.Field;

public class NoticeRequestDtoTest {
  public static NoticeRequestDto getNoticeRequestDtoFixture() throws Exception {
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();

    Field titleField = NoticeRequestDto.class.getDeclaredField("title");
    titleField.setAccessible(true);
    titleField.set(noticeRequestDto, "title");

    Field contentField = NoticeRequestDto.class.getDeclaredField("content");
    contentField.setAccessible(true);
    contentField.set(noticeRequestDto, "content");

    return noticeRequestDto;
  }

  public static NoticeRequestDto getNoticeRequestDtoFixture(String title, String content) throws Exception {
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();

    MyReflectionUtils.setField(noticeRequestDto, "title", title);
    MyReflectionUtils.setField(noticeRequestDto, "content", content);

    return noticeRequestDto;
  }

}

