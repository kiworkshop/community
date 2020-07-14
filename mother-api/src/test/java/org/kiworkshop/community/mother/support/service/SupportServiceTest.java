package org.kiworkshop.community.mother.support.service;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.support.api.dto.SupportResponseDto;

public class SupportServiceTest {
    private SupportService supportService;

    @BeforeEach
    void setup(){
        supportService = new SupportService();
    }

    @Test
    void ValidInput_ValidOutput(){
        SupportResponseDto supportResponseDto = supportService.readSupport();
        then(supportResponseDto)
                .hasFieldOrPropertyWithValue("title",supportService.getTitle())
                .hasFieldOrPropertyWithValue("content",supportService.getContent());
    }
}