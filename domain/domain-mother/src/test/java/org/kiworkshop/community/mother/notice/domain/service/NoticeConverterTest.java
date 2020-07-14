package org.kiworkshop.community.mother.notice.domain.service;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.dtos.NoticeRequestDto;
import org.kiworkshop.community.mother.dtos.NoticeRequestDtoFixture;
import org.kiworkshop.community.mother.dtos.NoticeResponseDto;
import org.kiworkshop.community.mother.notice.domain.model.Notice;
import org.kiworkshop.community.mother.notice.domain.model.NoticeFixture;

class NoticeConverterTest {

  @Test
  void toEntity_ValidInput_ValidOutput() {
    // given
    NoticeRequestDto noticeRequestDto = NoticeRequestDtoFixture.get();

    // when
    Notice notice = NoticeConverter.toEntity(noticeRequestDto);

    // then
    then(notice).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt");
    then(notice.getTitle()).isEqualTo(notice.getTitle());
    then(notice.getContent()).isEqualTo(notice.getContent());
  }

  @Test
  void toResponseDto_ValidInput_ValidOutput() {
    // given
    Notice notice = NoticeFixture.get();

    // when
    NoticeResponseDto noticeResponseDto = NoticeConverter.toResponseDto(notice);

    // then
    then(noticeResponseDto).hasNoNullFieldsOrProperties();
    then(noticeResponseDto.getId()).isEqualTo(notice.getId());
    then(noticeResponseDto.getTitle()).isEqualTo(notice.getTitle());
    then(noticeResponseDto.getContent()).isEqualTo(notice.getContent());
  }
}