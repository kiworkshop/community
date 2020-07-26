package org.kiworkshop.community.mother.notice.api;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.kiworkshop.community.CommunityApplication;
import org.kiworkshop.community.mother.dto.NoticeRequestDto;
import org.kiworkshop.community.mother.dto.NoticeRequestDtoFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest(classes = CommunityApplication.class, webEnvironment = RANDOM_PORT)
@Transactional
@Sql("/data/notices.sql")
class NoticeControllerIntTest {
  private @Autowired MockMvc mvc;
  private @Autowired ObjectMapper objectMapper;

  @Test
  @Disabled // TODO: remove when dev auth api is deployed.
  void postNotice_ValidInput_ValidOutput() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = NoticeRequestDtoFixture.get();

    // expect
    this.mvc.perform(post("/notices")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNumber());
  }

  @Test
  public void getNotice_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(get("/notices/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("title1"))
            .andExpect(jsonPath("$.content").value("content1"));
  }

  @Test
  @Disabled // TODO: remove when dev auth api is deployed.
  void deleteNoticeAndTryToGetNotice_ValidInput_StatusOkAndFailToGetIt() throws Exception {
    this.mvc.perform(delete("/notices/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());

    this.mvc.perform(get("/notices/{id}", 1L))
        .andExpect(status().isNotFound());
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
  @Disabled // TODO: remove when dev auth api is deployed.
  void updateNotice_ValidInput_ValidOutput() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = NoticeRequestDtoFixture.get("updated title", "updated content");

    // expect
    this.mvc.perform(put("/notices/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());

    this.mvc.perform(get("/notices/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("updated title"))
        .andExpect(jsonPath("$.content").value("updated content"));
  }
}
