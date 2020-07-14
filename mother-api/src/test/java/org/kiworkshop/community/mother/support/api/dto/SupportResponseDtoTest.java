package org.kiworkshop.community.mother.support.api.dto;

import static org.assertj.core.api.BDDAssertions.then;
import static org.kiworkshop.community.mother.support.domain.SupportTest.getSupportFixture;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.support.domain.Support;

public class SupportResponseDtoTest {

    public static SupportResponseDto getSupportResponseDtoFixture(){
        return SupportResponseDto.from(getSupportFixture());
    }

    @Test
    void generateSupportResponseDtoFromSupport_ValidInput_SupportResponseDtoOutput(){
        Support support = getSupportFixture();
        SupportResponseDto supportResponseDto = SupportResponseDto.from(support);

        then(supportResponseDto)
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("content", "content");
    }
}