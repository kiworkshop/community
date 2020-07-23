package org.kiworkshop.community.mother.notice.domain.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.mother.dto.NoticeRequestDto;
import org.kiworkshop.community.mother.dto.NoticeResponseDto;
import org.kiworkshop.community.mother.notice.domain.model.Notice;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeConverter {
  public static Notice toEntity(NoticeRequestDto dto) {
    return Notice.builder()
        .title(dto.getTitle())
        .content(dto.getContent())
        .build();
  }

  public static NoticeResponseDto toResponseDto(Notice notice) {
    return NoticeResponseDto.builder()
        .id(notice.getId())
        .title(notice.getTitle())
        .content(notice.getContent())
        .build();
  }
}
