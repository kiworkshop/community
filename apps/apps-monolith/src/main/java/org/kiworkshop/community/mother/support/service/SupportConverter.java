package org.kiworkshop.community.mother.support.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.mother.dtos.SupportResponseDto;
import org.kiworkshop.community.mother.support.domain.Support;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportConverter {
  public static SupportResponseDto toResponseDto(Support support) {
    return SupportResponseDto.builder()
        .title(support.getTitle())
        .content(support.getContent())
        .build();
  }
}
