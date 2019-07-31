package comunity.mother.notice.api;

import comunity.mother.notice.api.dto.NoticeRequestDto;
import comunity.mother.notice.service.NoticeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notices")
public class NoticeController {
  private final NoticeService noticeService;

  @PostMapping
  public Long create(@RequestBody @Valid NoticeRequestDto noticeRequestDto) {
    return noticeService.createNotice(noticeRequestDto);
  }

  @PutMapping(value = "/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid NoticeRequestDto noticeRequestDto) {
    noticeService.updateNotice(id, noticeRequestDto);
  }
}
