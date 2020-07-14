package org.kiworkshop.community.mother.about.converter;

import org.kiworkshop.community.mother.about.domain.About;
import org.kiworkshop.community.mother.dtos.AboutResponseDto;

public class AboutConverter {

  private AboutConverter() {}

  public static AboutResponseDto from(About about) {
    return AboutResponseDto.builder()
        .title(about.getTitle())
        .content(about.getContent())
        .build();
  }
}
