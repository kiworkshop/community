package community.mother.notice.api;

import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.service.NoticeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/notices")
public class NoticeController {
  private final NoticeService noticeService;

  @PostMapping
  public Long create(@RequestBody @Valid NoticeRequestDto noticeRequestDto) {
    return noticeService.createNotice(noticeRequestDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    noticeService.deleteById(id);
  }

  @GetMapping
  public Page<NoticeResponseDto> readPage(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return noticeService.readNoticePage(pageable);
  }

  @GetMapping("/{id}")
  public NoticeResponseDto read(@PathVariable Long id) {
    return noticeService.readNotice(id);
  }

  @PutMapping(value = "/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid NoticeRequestDto noticeRequestDto) {
    noticeService.updateNotice(id, noticeRequestDto);
  }
}
