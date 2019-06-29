package comunity.mother.notice.service;

import comunity.mother.notice.domain.Notice;
import comunity.mother.notice.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
  private final NoticeRepository noticeRepository;

  public Notice createNotice(Notice notice) {
    return noticeRepository.save(notice);
  }
}
