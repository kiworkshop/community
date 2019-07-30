package community.mother.notice.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeRequestDtoTest;
import community.mother.notice.service.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class NoticeControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean NoticeService noticeService;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void createNotice_ValidInput_ValidOutput() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = NoticeRequestDtoTest.getNoticeRequestDtoFixture();
    given(noticeService.createNotice(any(NoticeRequestDto.class))).willReturn(1L);

    // expect
    this.mvc.perform(post("/notices")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(noticeRequestDto)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").value(1L));
  }
}
