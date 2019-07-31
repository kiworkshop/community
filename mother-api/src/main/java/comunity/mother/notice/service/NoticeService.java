package comunity.mother.notice.service;

import comunity.mother.notice.api.dto.NoticeRequestDto;
import comunity.mother.notice.domain.Notice;
import comunity.mother.notice.domain.NoticeRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
  private final NoticeRepository noticeRepository;
  private final ModelMapper modelMapper = new ModelMapper();

  public Long createNotice(NoticeRequestDto noticeRequestDto) {
    Notice notice = modelMapper.map(noticeRequestDto, Notice.class);
    return noticeRepository.save(notice).getId();
  }

  public void updateNotice(Long id, NoticeRequestDto noticeRequestDto) {
    Notice savedNotice = noticeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    noticeRepository.save(savedNotice.updateNotice(noticeRequestDto));
  }
}
