package community.mother.support.api.dto;

import community.mother.support.domain.Support;
import org.junit.jupiter.api.Test;

import static community.mother.support.domain.SupportTest.getSupportFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

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