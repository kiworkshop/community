package community.mother.notice.api.dto;

import community.mother.notice.domain.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {
  private Long id;
  private String title;
  private String content;

  private NoticeResponseDto(Notice notice) {
    this.id = notice.getId();
    this.title = notice.getTitle();
    this.content = notice.getContent();
  }

  public static NoticeResponseDto of(Notice notice) {
    return new NoticeResponseDto(notice);
  }
}
