package org.kiworkshop.community.mother.notice.api.dto;

import static org.assertj.core.api.BDDAssertions.then;
import static org.kiworkshop.community.mother.notice.domain.NoticeTest.getNoticeFixture;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.notice.domain.Notice;

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

  @Test
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
