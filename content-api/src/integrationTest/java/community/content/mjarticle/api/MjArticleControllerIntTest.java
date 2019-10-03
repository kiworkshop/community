package community.content.mjarticle.api;

import static community.content.mjarticle.api.dto.MjArticleRequestDtoTest.getMjArticleRequestDtoFixture;
import static community.content.mjarticle.api.dto.MjArticleResponseDtoTest.getMjArticleResponseDtoFixture;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.ContentApiApplication;
import community.content.mjarticle.api.dto.MjArticleRequestDto;
import community.content.mjarticle.api.dto.MjArticleResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = ContentApiApplication.class, webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Sql("/data/test-mjarticle-data.sql")
class MjArticleControllerIntTest {
  private @Autowired MockMvc mvc;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void createMjArticle_ValidInput_ValidOutput() throws Exception {
    MjArticleRequestDto mjArticleRequestDto = getMjArticleRequestDtoFixture();

    // expect
    this.mvc.perform(post("/myeongjae/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mjArticleRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(11L));
  }

  @Test
  void createMjArticle_NullFields_StatusBadRequest() throws Exception {
    // given
    MjArticleRequestDto mjArticleRequestDto = new MjArticleRequestDto();

    // expect
    this.mvc.perform(post("/myeongjae/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mjArticleRequestDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void readMjArticles_ValidInput_ValidOutput() throws Exception {
    // expect
    this.mvc.perform(get("/myeongjae/articles"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(10))
        .andExpect(jsonPath("$.content[9].id").value(1))
        .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
        .andExpect(jsonPath("$.pageable.pageSize").value(10));
  }

  @Test
  void get_ValidInput_MjArticleResponse() throws Exception {
    MjArticleResponseDto mjArticleResponseDto = getMjArticleResponseDtoFixture();

    this.mvc.perform(get("/myeongjae/articles/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("title"))
        .andExpect(jsonPath("$.content").value("content"));
  }

  @Test
  void get_NonExistentId_ApiError() throws Exception {
    this.mvc.perform(get("/myeongjae/articles/{id}", 1 << 20))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void updateMjArticle_ValidInput_ValidOutput() throws Exception {
    // given
    MjArticleRequestDto mjArticleRequestDto = getMjArticleRequestDtoFixture();

    // expect
    this.mvc.perform(put("/myeongjae/articles/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mjArticleRequestDto)));
  }

  @Test
  void deleteMjArticle_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(delete("/myeongjae/articles/{id}", 1L))
        .andExpect(status().isOk());
  }
}
