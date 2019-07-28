package community.mother.notice.api.dto;

import static community.mother.notice.domain.NoticeTest.getNoticeFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.mother.notice.domain.Notice;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

class NoticeResponseDtoTest {
  private final ModelMapper modelMapper = new ModelMapper();

  @Test
  void construct_ValidInput_ValidOutput() throws Exception {
    // given
    Notice notice = getNoticeFixture();
    modelMapper.getConfiguration()
        .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
        .setFieldMatchingEnabled(true);

    // when
    NoticeResponseDto noticeResponseDto = modelMapper.map(notice, NoticeResponseDto.class);

    // then
    then(noticeResponseDto.getId()).isEqualTo(notice.getId());
    then(noticeResponseDto.getTitle()).isEqualTo(notice.getTitle());
    then(noticeResponseDto.getContent()).isEqualTo(notice.getContent());
  }
}
