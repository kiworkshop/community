package community.mother.notice.service;

import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.domain.Notice;
import community.mother.notice.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<NoticeResponseDto> readNotices(Pageable pageable) {
    return noticeRepository.findAll(pageable).map(NoticeResponseDto::of);
  }
}
