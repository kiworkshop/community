package org.kiworkshop.community.mother.about.api.dto;

import static org.assertj.core.api.BDDAssertions.then;
import static org.kiworkshop.community.mother.about.domain.AboutTest.getAboutFixture;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.about.domain.About;

public class AboutResponseDtoTest {

    public static AboutResponseDto getAboutResponseDtoFixture() throws Exception{
        return AboutResponseDto.from(getAboutFixture());
    }

    @Test
    void generateAboutResponseDtoFromAbout_ValidInput_ReturnAboutResponseDto() throws Exception{
        About about = getAboutFixture();
        AboutResponseDto aboutResponseDto = AboutResponseDto.from(about);

        then(aboutResponseDto)
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("title",about.getTitle())
                .hasFieldOrPropertyWithValue("content",about.getContent());
    }
}
