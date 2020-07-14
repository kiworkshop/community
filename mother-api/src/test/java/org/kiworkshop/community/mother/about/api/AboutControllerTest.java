package org.kiworkshop.community.mother.about.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.about.service.AboutService;
import org.kiworkshop.community.mother.dtos.AboutResponseDto;
import org.kiworkshop.community.mother.dtos.AboutResponseDtoFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AboutController.class)
public class AboutControllerTest {
    private @Autowired MockMvc mvc;
    private @MockBean
    AboutService aboutService;

    @Test
    void get_ValidInput_AboutResponse() throws Exception{
        AboutResponseDto aboutResponseDto = AboutResponseDtoFixture.get();
        given(aboutService.readAbout()).willReturn(aboutResponseDto);

        this.mvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("content").value("content"));
    }
}
