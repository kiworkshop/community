package org.kiworkshop.community.mother.support.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.mother.dto.SupportResponseDto;
import org.kiworkshop.community.mother.dto.SupportResponseDtoFixture;
import org.kiworkshop.community.mother.support.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SupportController.class)
public class SupportControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean SupportService supportService;

  @Test
  void get_ValidInput_SupportResponse() throws Exception {
    SupportResponseDto supportResponseDto = SupportResponseDtoFixture.get();
    given(supportService.readSupport()).willReturn(supportResponseDto);

    this.mvc.perform(get("/support"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("title").value("title"))
        .andExpect(jsonPath("content").value("content"));
  }
}