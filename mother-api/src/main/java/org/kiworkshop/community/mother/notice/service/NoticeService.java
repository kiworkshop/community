package org.kiworkshop.community.mother.notice.service;

import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.mother.dtos.NoticeRequestDto;
import org.kiworkshop.community.mother.dtos.NoticeResponseDto;
import org.kiworkshop.community.mother.notice.converter.NoticeConverter;
import org.kiworkshop.community.mother.notice.domain.Notice;
import org.kiworkshop.community.mother.notice.domain.NoticeRepository;
import org.kiworkshop.community.mother.notice.exception.NoticeNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
  private final NoticeRepository noticeRepository;

  public Long createNotice(NoticeRequestDto noticeRequestDto) {
    return noticeRepository.save(NoticeConverter.toEntity(noticeRequestDto)).getId();
  }

  public Page<NoticeResponseDto> readNoticePage(Pageable pageable) {
    return noticeRepository.findAll(pageable).map(NoticeConverter::toResponseDto);
  }

  public NoticeResponseDto readNotice(Long id) {
    return NoticeConverter.toResponseDto(findNoticeById(id));
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
