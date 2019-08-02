package community.mother.notice.service;

import static community.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;
import static community.mother.notice.domain.NoticeTest.getNoticeFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.domain.Notice;
import community.mother.notice.domain.NoticeRepository;
import community.mother.notice.exception.NoticeNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


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
  void readNoticePage_ValidInput_ValidOutput() throws Exception {
    // given
    final Long numNotices = 10L;
    List<Notice> notices = new ArrayList<>();

    for (long i = 0; i < numNotices; i++) {
      notices.add(getNoticeFixture(i));
    }

    PageImpl<Notice> noticePage = new PageImpl<>(notices);
    given(noticeRepository.findAll(any(Pageable.class))).willReturn(noticePage);

    // when
    Page<NoticeResponseDto> noticeResponseDtoPage = noticeService.readNoticePage(
        PageRequest.of(0, numNotices.intValue()));

    // expect
    then(noticeResponseDtoPage.getTotalElements()).isEqualTo(numNotices);
  }

  @Test
  void readNotice_ValidInput_FoundNotice() throws Exception {
    Notice notice = getNoticeFixture();
    given(noticeRepository.findById(anyLong())).willReturn(Optional.of(notice));

    NoticeResponseDto foundNotice = noticeService.readNotice(1L);

    then(foundNotice)
        .hasFieldOrPropertyWithValue("id", notice.getId())
        .hasFieldOrPropertyWithValue("title", notice.getTitle())
        .hasFieldOrPropertyWithValue("content", notice.getContent());
  }

  @Test
  void readNotice_NonExistentNoticeId_NoticeNotFoundException() {
    given(noticeRepository.findById(anyLong())).willReturn(Optional.empty());
    thenThrownBy(() -> noticeService.readNotice(1L)).isExactlyInstanceOf(NoticeNotFoundException.class);
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

  @Test
  void deleteNotice_ValidInput_DeleteNotice() throws Exception {
    // given
    Notice noticeToDelete = getNoticeFixture();
    given(noticeRepository.findById(any(Long.class))).willReturn(Optional.of(noticeToDelete));

    // when
    noticeService.deleteById(noticeToDelete.getId());
  }

  @Test
  void deleteNotice_InvalidInput_Exception() {
    given(noticeRepository.findById(any(Long.class))).willReturn(Optional.empty());
    thenThrownBy(() -> noticeService.deleteById(1L))
        .isExactlyInstanceOf(NoticeNotFoundException.class);
  }
}
