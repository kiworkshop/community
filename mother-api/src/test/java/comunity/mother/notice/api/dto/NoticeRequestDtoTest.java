package comunity.mother.notice.api.dto;

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
}

