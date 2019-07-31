package community.mother.notice.api.dto;

import static community.mother.notice.domain.NoticeTest.getNoticeFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.mother.notice.domain.Notice;
import org.junit.jupiter.api.Test;

public class NoticeResponseDtoTest {
  public static NoticeResponseDto getNoticeResponseFixture() throws Exception {
    return NoticeResponseDto.of(getNoticeFixture());
  }

  @Test
  void generateNoticeResponseFromNotice_ValidInput_ReturnNoticeResponseDto() throws Exception {
    Notice notice = getNoticeFixture();
    NoticeResponseDto noticeResponseDto = NoticeResponseDto.of(notice);

    then(noticeResponseDto)
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("id", notice.getId())
            .hasFieldOrPropertyWithValue("title", notice.getTitle())
            .hasFieldOrPropertyWithValue("content", notice.getContent());
  }
}