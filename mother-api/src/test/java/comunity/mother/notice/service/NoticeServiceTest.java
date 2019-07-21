package comunity.mother.notice.service;

import static comunity.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;
import static comunity.mother.notice.domain.NoticeTest.getNoticeFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import comunity.mother.notice.api.dto.NoticeRequestDto;
import comunity.mother.notice.domain.Notice;
import comunity.mother.notice.domain.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {
  private NoticeService noticeService;

  private @Mock NoticeRepository noticeRepository;

  @BeforeEach
  void setup() {
    noticeService = new NoticeService(noticeRepository, new ModelMapper());
  }

  @Test
  void createNotice_ValidInput_CreatedNotice() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = getNoticeRequestDtoFixture();

    Notice noticeToSave = getNoticeFixture();
    given(noticeRepository.save(any(Notice.class))).willReturn(noticeToSave);

    // when
    Long id = noticeService.createNotice(noticeRequestDto);

    then(id).isEqualTo(noticeToSave.getId());
  }
}
