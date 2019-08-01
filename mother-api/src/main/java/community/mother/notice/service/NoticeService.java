package community.mother.notice.service;

import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.domain.Notice;
import community.mother.notice.domain.NoticeRepository;
import community.mother.notice.exception.NoticeNotFoundException;
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

  public void deleteById(Long id) {
    Notice noticeToDelete = noticeRepository.findById(id).orElseThrow(() -> new NoticeNotFoundException(id));
    noticeRepository.delete(noticeToDelete);
  }
}
