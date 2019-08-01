package community.mother.notice.api;

import static community.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.mother.MotherApiApplication;
import community.mother.notice.api.dto.NoticeRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest(classes = MotherApiApplication.class, webEnvironment = RANDOM_PORT)
@Transactional
@Sql("/data/notices.sql")
class NoticeControllerIntTest {
  private @Autowired MockMvc mvc;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void postNotice_ValidInput_ValidOutput() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = getNoticeRequestDtoFixture();

    // expect
    this.mvc.perform(post("/notices")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNumber());
  }

  @Test
  void readNotices_NoParams_DefaultPageSortedTrue() throws Exception {
    // expect
    this.mvc.perform(get("/notices"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(10))
        .andExpect(jsonPath("$.content[9].id").value(1))
        .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
        .andExpect(jsonPath("$.pageable.pageSize").value(10));
  }

  @Test
  void readNotices_PageNumber1_ReturnFirstPage() throws Exception {
    // expect
    this.mvc.perform(get("/notices")
        .param("page", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(10))
        .andExpect(jsonPath("$.content[9].id").value(1))
        .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
        .andExpect(jsonPath("$.pageable.pageSize").value(10));
  }

  @Test
  void updateNotice_ValidInput_ValidOutput() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = getNoticeRequestDtoFixture();

    // expect
    this.mvc.perform(put("/notices/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isOk());
  }
}
