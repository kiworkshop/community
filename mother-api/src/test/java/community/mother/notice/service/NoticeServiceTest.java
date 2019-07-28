package community.mother.notice.service;

import static community.mother.notice.domain.NoticeTest.getNoticeFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeRequestDtoTest;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.domain.Notice;
import community.mother.notice.domain.NoticeRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
    NoticeRequestDto noticeRequestDto = NoticeRequestDtoTest.getNoticeRequestDtoFixture();

    Notice noticeToSave = getNoticeFixture();
    given(noticeRepository.save(any(Notice.class))).willReturn(noticeToSave);

    // when
    Long id = noticeService.createNotice(noticeRequestDto);

    then(id).isEqualTo(noticeToSave.getId());
  }

  @Test
  void readNotices_WithPageRequest_PaginatedNotices() throws Exception {
    // given
    PageRequest pageRequest = PageRequest.of(0, 10);
    List<Notice> givenNotices = new ArrayList<>();
    for(int i = 0; i < 10; i++) {
      givenNotices.add(getNoticeFixture());
    }

    PageImpl<Notice> pageImpl = new PageImpl<>(givenNotices, pageRequest, 10);
    given(noticeRepository.findAll(any(PageRequest.class))).willReturn(pageImpl);

    // when
    Page<NoticeResponseDto> notices = noticeService.readNotices(pageRequest);

    // then
    then(notices.getTotalElements()).isEqualTo(10);
    then(notices.getTotalPages()).isEqualTo(1);
  }
}
