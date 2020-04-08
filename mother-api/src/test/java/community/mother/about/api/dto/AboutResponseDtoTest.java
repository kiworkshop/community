package community.mother.about.api.dto;

import community.mother.about.domain.About;
import org.junit.jupiter.api.Test;

import static community.mother.about.domain.AboutTest.getAboutFixture;
import static org.assertj.core.api.BDDAssertions.then;

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
