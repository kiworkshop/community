package community.mother.support.api;

import community.mother.support.api.dto.SupportResponseDto;
import community.mother.support.service.SupportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static community.mother.support.api.dto.SupportResponseDtoTest.getSupportResponseDtoFixture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SupportControllerTest {
    private @Autowired MockMvc mvc;
    private @MockBean SupportService supportService;

    @Test
    void get_ValidInput_SupportResponse() throws Exception{
        SupportResponseDto supportResponseDto = getSupportResponseDtoFixture();
        given(supportService.readSupport()).willReturn(supportResponseDto);

        this.mvc.perform(get("/support"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("content").value("content"));
    }
}