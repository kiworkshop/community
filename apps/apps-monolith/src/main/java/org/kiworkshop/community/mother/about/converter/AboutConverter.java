package org.kiworkshop.community.mother.about.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.mother.about.domain.About;
import org.kiworkshop.community.mother.dtos.AboutResponseDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AboutConverter {

  public static AboutResponseDto from(About about) {
    return AboutResponseDto.builder()
        .title(about.getTitle())
        .content(about.getContent())
        .build();
  }
}
