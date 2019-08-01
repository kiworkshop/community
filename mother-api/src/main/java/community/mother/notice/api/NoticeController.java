package community.mother.notice.api;

import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.exception.NoticeNotFoundException;
import community.mother.notice.service.NoticeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notices")
public class NoticeController {
  private final NoticeService noticeService;

  @PostMapping
  public Long create(@RequestBody @Valid NoticeRequestDto noticeRequestDto) {
    return noticeService.createNotice(noticeRequestDto);
  }

  // TODO: Delete when read method is implemented.
  @GetMapping("/not-found")
  public void occurNoticeNotFoundExceptionForTest() {
    throw new NoticeNotFoundException(1L);
  }

  @GetMapping
  public Page<NoticeResponseDto> readPage(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return noticeService.readNoticePage(pageable);
  }
}
