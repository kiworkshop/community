package community.mother.notice.service;

import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.domain.Notice;
import community.mother.notice.domain.NoticeRepository;
import community.mother.notice.exception.NoticeNotFoundException;
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

  public Page<NoticeResponseDto> readNoticePage(Pageable pageable) {
    return noticeRepository.findAll(pageable).map(NoticeResponseDto::of);
  }

  public NoticeResponseDto readNotice(Long id) {
    return NoticeResponseDto.of(findNoticeById(id));
  }

  public void updateNotice(Long id, NoticeRequestDto noticeRequestDto) {
    Notice noticeToUpdate = findNoticeById(id);
    noticeToUpdate.updateNotice(noticeRequestDto.getTitle(), noticeRequestDto.getContent());
    noticeRepository.save(noticeToUpdate);
  }

  public void deleteById(Long id) {
    Notice noticeToDelete = findNoticeById(id);
    noticeRepository.delete(noticeToDelete);
  }

  private Notice findNoticeById(Long id) {
    return noticeRepository.findById(id).orElseThrow(() -> new NoticeNotFoundException(id));
  }
}
