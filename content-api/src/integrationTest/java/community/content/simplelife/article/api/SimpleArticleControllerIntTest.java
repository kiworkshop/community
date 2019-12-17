package community.content.simplelife.article.api;

import static community.content.simplelife.article.api.dto.SimpleArticleRequestDtoTest.getArticleRequestDtoFixture;
import static community.content.simplelife.article.api.dto.SimpleArticleResponseDtoTest.getPostResponseDtoFixture;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.ContentApiApplication;
import community.content.simplelife.article.api.dto.SimpleArticleRequestDto;
import community.content.simplelife.article.api.dto.SimpleArticleResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest(classes = ContentApiApplication.class, webEnvironment = RANDOM_PORT)
@Transactional
@Sql("/data/simple-article.sql")
public class SimpleArticleControllerIntTest {
  private @Autowired MockMvc mvc;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void createPost_ValidInput_ValidOutput() throws Exception {
    // given
    SimpleArticleRequestDto simpleArticleRequestDto = getArticleRequestDtoFixture();

    // expect
    this.mvc.perform(post("/simplelife/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(simpleArticleRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(11L));
  }

  @Test
  void createPost_NullFields_StatusBadRequest() throws Exception {
    // given
    SimpleArticleRequestDto simpleArticleRequestDto = new SimpleArticleRequestDto();

    // expect
    this.mvc.perform(post("/simplelife/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(simpleArticleRequestDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getPosts_ValidInput_ValidOutput() throws Exception {
    // expect
    this.mvc.perform(get("/simplelife/articles"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(10))
        .andExpect(jsonPath("$.content[9].id").value(1))
        .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
        .andExpect(jsonPath("$.pageable.pageSize").value(10));
  }

  @Test
  void getPost_ValidInput_PostResponse() throws Exception {
    SimpleArticleResponseDto simpleArticleResponseDto = getPostResponseDtoFixture();

    this.mvc.perform(get("/simplelife/articles/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(1L))
        .andExpect(jsonPath("title").value("title"))
        .andExpect(jsonPath("description").value("description"))
        .andExpect(jsonPath("content").value("content"));
  }

  @Test
  void getPost_NonExistentId_ApiError() throws Exception {
    this.mvc.perform(get("/simplelife/articles/{id}", 1000L))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void updatePost_ValidInput_ValidOutput() throws Exception {
    // given
    SimpleArticleRequestDto simpleArticleRequestDto = getArticleRequestDtoFixture();

    // expect
    this.mvc.perform(post("/simplelife/articles/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(simpleArticleRequestDto)))
        .andExpect(status().isOk());
  }

  @Test
  void deletePost_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(delete("/simplelife/articles/{id}", 1))
        .andExpect(status().isOk());
  }
}
