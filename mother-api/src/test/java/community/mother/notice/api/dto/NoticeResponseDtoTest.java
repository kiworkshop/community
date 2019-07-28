package community.mother.notice.api.dto;

import static community.mother.notice.domain.NoticeTest.getNoticeFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.mother.notice.domain.Notice;
import org.junit.jupiter.api.Test;

public class NoticeResponseDtoTest {
  public static NoticeResponseDto getNoticeResponseDtoFixture(Long id) throws Exception {
    return NoticeResponseDto.of(getNoticeFixture(id));
  }

  @Test
  void construct_ValidInput_ValidOutput() throws Exception {
    // given
    Notice notice = getNoticeFixture();

    // when
    NoticeResponseDto noticeResponseDto = NoticeResponseDto.of(notice);

    // then
    then(noticeResponseDto.getId()).isEqualTo(notice.getId());
    then(noticeResponseDto.getTitle()).isEqualTo(notice.getTitle());
    then(noticeResponseDto.getContent()).isEqualTo(notice.getContent());
  }
}
