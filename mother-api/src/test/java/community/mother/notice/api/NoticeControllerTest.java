package community.mother.notice.api;

import static community.mother.notice.api.dto.NoticeResponseDtoTest.getNoticeResponseDtoFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeRequestDtoTest;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.service.NoticeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  void createNotice_NullFields_StatusBadRequest() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();

    // expect
    this.mvc.perform(post("/notices")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isBadRequest());
  }

  // TODO: Delete when read method is implemented.
  @Test
  void occurNoticeNotFoundExceptionForTest_ValidInput_ThrowException() throws Exception {
    this.mvc.perform(get("/notices/not-found"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("Not Found"))
        .andExpect(jsonPath("$.message").value("notice id 1 has not been found"));
  }

  @Test
  void readNotices_ValidInput_ValidOutput() throws Exception {
    // given
    List<NoticeResponseDto> noticeResponseDtos = new ArrayList<>();
    final var numNotices = 10L;
    for (long i = 0; i < numNotices; i++) {
      noticeResponseDtos.add(getNoticeResponseDtoFixture(i + 1));
    }
    Collections.reverse(noticeResponseDtos);
    PageImpl<NoticeResponseDto> noticeResponseDtoPage = new PageImpl<>(
        noticeResponseDtos,
        PageRequest.of(0, 10, Sort.Direction.DESC, "id"),
        10);
    given(noticeService.readNoticePage(any(Pageable.class))).willReturn(noticeResponseDtoPage);

    // expect
    this.mvc.perform(get("/notices"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(10))
        .andExpect(jsonPath("$.content[9].id").value(1))
        .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
        .andExpect(jsonPath("$.pageable.pageSize").value(10));
  }

  @Test
  void deleteNotice_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(delete("/notices/{id}", 1L))
        .andExpect(status().isOk());
  }
}
