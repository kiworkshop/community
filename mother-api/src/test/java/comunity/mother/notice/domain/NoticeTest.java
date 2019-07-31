package comunity.mother.notice.domain;

import static comunity.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import comunity.mother.notice.api.dto.NoticeRequestDto;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

public class NoticeTest {
  public static Notice getNoticeFixture() throws Exception {
    Notice notice = Notice.builder().title("title").content("content").build();
    Field idField = Notice.class.getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(notice, 1L);

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
        .hasNoNullFieldsOrPropertiesExcept("id")
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
    Notice savedNotice = getNoticeFixture();
    NoticeRequestDto noticeRequestDto = getNoticeRequestDtoFixture();

    // when
    Notice updatedNotice = savedNotice.updateNotice(noticeRequestDto);
    then(updatedNotice)
        .hasFieldOrPropertyWithValue("title", noticeRequestDto.getTitle())
        .hasFieldOrPropertyWithValue("content", noticeRequestDto.getContent());
  }
}
