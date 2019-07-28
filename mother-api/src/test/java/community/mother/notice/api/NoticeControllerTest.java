package community.mother.notice.api;

import static community.mother.notice.api.dto.NoticeResponseDtoTest.getNoticeResponseDtoFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.common.util.MyReflectionUtils;
import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeRequestDtoTest;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.service.NoticeService;
import java.util.ArrayList;
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
  void createNotice_EmptyContents_ValidOutput() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();
    MyReflectionUtils.setField(noticeRequestDto, "title", "title");

    // expect
    this.mvc.perform(post("/notices")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.message")
            .value("content must not be empty."));
  }

  // TODO: Delete when test of read method is implemented.
  @Test
  void occurNoticeNotFoundException_ValidInput_ReturnApiError() throws Exception {
    // expect
    this.mvc.perform(get("/notices/not-found"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("Not Found"))
        .andExpect(jsonPath("$.message").value("notice id 1 has not been found"));
  }

  @Test
  void readPage_ValidInput_ValidOutput() throws Exception {
    // given
    final int numTotal = 10;
    List<NoticeResponseDto> noticeResponseDtos = new ArrayList<>();
    for (long i = 0; i < numTotal; i++) {
      noticeResponseDtos.add(getNoticeResponseDtoFixture(numTotal - i));
    }

    PageImpl<NoticeResponseDto> noticeResponseDtoPage = new PageImpl<>(
        noticeResponseDtos,
        PageRequest.of(0, numTotal, new Sort(Sort.Direction.DESC, "id")),
        numTotal);
    given(noticeService.readNotices(any(Pageable.class))).willReturn(noticeResponseDtoPage);

    // when
    this.mvc.perform(get("/notices")
        .param("page", "0")
        .param("size", Integer.toString(numTotal))
        .param("sort", "id,desc"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(10))
        .andExpect(jsonPath("$.content[9].id").value(1))
        .andExpect(jsonPath("$.sort.sorted").value(true));
  }
}
