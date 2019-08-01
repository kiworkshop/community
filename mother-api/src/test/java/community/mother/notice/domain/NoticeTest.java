package community.mother.notice.domain;

import static community.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import community.common.util.MyReflectionUtils;
import community.mother.notice.api.dto.NoticeRequestDto;
import org.junit.jupiter.api.Test;

public class NoticeTest {
  public static Notice getNoticeFixture() throws Exception {
    return getNoticeFixture(1L);
  }

  public static Notice getNoticeFixture(Long id) throws Exception {
    Notice notice = Notice.builder().title("title").content("content").build();
    MyReflectionUtils.setField(notice, "id", id);

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
