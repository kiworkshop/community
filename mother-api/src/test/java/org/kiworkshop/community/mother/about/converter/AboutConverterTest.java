package org.kiworkshop.community.mother.about.converter;

import static org.assertj.core.api.BDDAssertions.then;
import static org.kiworkshop.community.mother.about.domain.AboutTest.getAboutFixture;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.about.domain.About;
import org.kiworkshop.community.mother.dtos.AboutResponseDto;

class AboutConverterTest {

  @Test
  void from_ValidInput_ValidOutput() throws Exception {
    About about = getAboutFixture();
    AboutResponseDto aboutResponseDto = AboutConverter.from(about);

    then(aboutResponseDto)
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("title",about.getTitle())
        .hasFieldOrPropertyWithValue("content",about.getContent());
  }
}