package org.kiworkshop.community.mother.support.service;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.dtos.SupportResponseDto;

public class SupportServiceTest {
  private SupportService supportService;

  @BeforeEach
  void setup() {
    supportService = new SupportService();
  }

  @Test
  void validInput_ValidOutput() {
    SupportResponseDto supportResponseDto = supportService.readSupport();
    then(supportResponseDto)
        .hasFieldOrPropertyWithValue("title", supportResponseDto.getTitle())
        .hasFieldOrPropertyWithValue("content",supportResponseDto.getContent());
  }
}