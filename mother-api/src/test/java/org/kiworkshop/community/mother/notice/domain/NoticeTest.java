package org.kiworkshop.community.mother.notice.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.kiworkshop.community.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.notice.api.dto.NoticeRequestDto;
import org.springframework.test.util.ReflectionTestUtils;

public class NoticeTest {
  public static Notice getNoticeFixture() throws Exception {
    return getNoticeFixture(1L);
  }

  public static Notice getNoticeFixture(Long id) throws Exception {
    Notice notice = Notice.builder().title("title").content("content").build();
    ReflectionTestUtils.setField(notice, "id", id);

    return notice;
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // given
    Notice notice = Notice.builder()
        .title("title")
        .content("content").build();

    // then
    then(notice)
          .hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt")
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("content", "content");
  }

  @Test
  void build_EmptyTitle_ThrowException() {
    // given
    thenThrownBy(() ->
        Notice.builder()
            .title("")
            .content("content").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void update_ValidInput_ValidOutput() throws Exception {
    //given
    Notice notice = getNoticeFixture();
    NoticeRequestDto noticeRequestDto = getNoticeRequestDtoFixture("updated title", "updated content");

    // when
    notice.updateNotice(noticeRequestDto.getTitle(), noticeRequestDto.getContent());
    then(notice)
        .hasFieldOrPropertyWithValue("title", noticeRequestDto.getTitle())
        .hasFieldOrPropertyWithValue("content", noticeRequestDto.getContent());
  }
}
