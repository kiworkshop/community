package community.mother.notice.service;

import static community.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;
import static community.mother.notice.domain.NoticeTest.getNoticeFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.domain.Notice;
import community.mother.notice.domain.NoticeRepository;
import community.mother.notice.exception.NoticeNotFoundException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {
  private NoticeService noticeService;

  private @Mock NoticeRepository noticeRepository;

  @BeforeEach
  void setup() {
    noticeService = new NoticeService(noticeRepository);
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

  @Test
  void updateNotice_validInput_validOutput() throws Exception {
    NoticeRequestDto noticeRequestDto = getNoticeRequestDtoFixture();
    Notice notice = getNoticeFixture();
    given(noticeRepository.findById(any(Long.class))).willReturn(Optional.of(notice));
    given(noticeRepository.save(any(Notice.class))).willReturn(notice);

    noticeService.updateNotice(1L, noticeRequestDto);
  }

  @Test
  void updateNotice_nonExistNotice_throwException() throws Exception {
    NoticeRequestDto noticeUpdatingRequestDto = getNoticeRequestDtoFixture();
    given(noticeRepository.findById(anyLong())).willReturn(Optional.empty());

    thenThrownBy(() -> noticeService.updateNotice(1L, noticeUpdatingRequestDto))
        .isInstanceOf(NoticeNotFoundException.class);
  }

}
