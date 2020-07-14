package org.kiworkshop.community.mother.support.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.kiworkshop.community.mother.support.domain.SupportTest.getSupportFixture;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.dtos.SupportResponseDto;
import org.kiworkshop.community.mother.support.domain.Support;

class SupportConverterTest {

  @Test
  void toResponseDto_ValidInput_ValidOutput() {
    // given
    Support support = getSupportFixture();

    // when
    SupportResponseDto dto = SupportConverter.toResponseDto(support);

    // then
    then(dto).hasNoNullFieldsOrProperties();
    then(dto.getTitle()).isEqualTo(support.getTitle());
    then(dto.getContent()).isEqualTo(support.getContent());
  }
}