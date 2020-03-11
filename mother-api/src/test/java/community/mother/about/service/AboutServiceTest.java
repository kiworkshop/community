package community.mother.about.service;

import community.mother.about.api.dto.AboutResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class AboutServiceTest {
    private AboutService aboutService;

    @BeforeEach
    void setup(){
        aboutService = new AboutService();
    }

    @Test
    void read_ValidInput_ValidOutput(){
        //given
        AboutResponseDto aboutResponseDto = aboutService.readAbout();

        //then
        then(aboutResponseDto)
                .hasFieldOrPropertyWithValue("title", aboutService.getTitle())
                .hasFieldOrPropertyWithValue("content", aboutService.getContent());
    }

}
