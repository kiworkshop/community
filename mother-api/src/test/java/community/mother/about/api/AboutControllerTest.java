package community.mother.about.api;

import community.mother.about.api.dto.AboutResponseDto;
import community.mother.about.service.AboutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static community.mother.about.api.dto.AboutResponseDtoTest.getAboutResponseDtoFixture;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AboutControllerTest {
    private @Autowired MockMvc mvc;
    private @MockBean AboutService aboutService;

    @Test
    void get_ValidInput_AboutResponse() throws Exception{
        AboutResponseDto aboutResponseDto = getAboutResponseDtoFixture();
        given(aboutService.readAbout()).willReturn(aboutResponseDto);

        this.mvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("content").value("content"));
    }
}
