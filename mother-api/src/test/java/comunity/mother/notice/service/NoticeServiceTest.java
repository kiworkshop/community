package comunity.mother.notice.service;

import static comunity.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;
import static comunity.mother.notice.domain.NoticeTest.getNoticeFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import comunity.mother.notice.api.dto.NoticeRequestDto;
import comunity.mother.notice.domain.Notice;
import comunity.mother.notice.domain.NoticeRepository;

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
    Long updatingNoticeId = 100L;
    given(noticeRepository.findById(updatingNoticeId)).willReturn(Optional.empty());

    thenThrownBy(() -> noticeService.updateNotice(updatingNoticeId, noticeUpdatingRequestDto))
        .isInstanceOf(EntityNotFoundException.class);
  }

}
