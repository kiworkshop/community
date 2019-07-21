package comunity.mother.notice.api;

import javax.validation.Valid;

import comunity.mother.notice.api.dto.NoticeRequestDto;
import comunity.mother.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
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
}
