package community.mother.notice.api;

import static community.mother.notice.api.dto.NoticeResponseDtoTest.getNoticeResponseFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeRequestDtoTest;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.exception.NoticeNotFoundException;
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

  @Test
  void get_ValidInput_NoticeResponse() throws Exception {
    NoticeResponseDto noticeResponseDto = getNoticeResponseFixture();
    given(noticeService.readNotice(anyLong())).willReturn(noticeResponseDto);

    this.mvc.perform(get("/notices/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1L))
            .andExpect(jsonPath("title").value("title"))
            .andExpect(jsonPath("content").value("content"));
  }

  @Test
  void get_NonExistentId_ApiError() throws Exception {
    given(noticeService.readNotice(anyLong())).willThrow(new NoticeNotFoundException(1L));

    this.mvc.perform(get("/notices/{id}", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("status").isNumber())
            .andExpect(jsonPath("error").isNotEmpty())
            .andExpect(jsonPath("message").isNotEmpty());
  }
}
