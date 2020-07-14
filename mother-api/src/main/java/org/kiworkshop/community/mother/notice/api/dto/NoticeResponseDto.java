package org.kiworkshop.community.mother.notice.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.mother.notice.domain.Notice;

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
