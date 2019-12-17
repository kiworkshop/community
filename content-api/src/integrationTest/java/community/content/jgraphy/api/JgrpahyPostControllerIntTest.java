package community.content.jgraphy.api;

import static community.content.jgraphy.api.dto.JgraphyPostRequestDtoTest.getJgraphyPostRequestDtoFixture;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.ContentApiApplication;
import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
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
@Sql("/data/jgraphyPost.sql")
@Transactional
class JgrpahyPostControllerIntTest {
  private @Autowired MockMvc mvc;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void createJgraphyPost_ValidInput_ValidOutput() throws Exception {
    // given
    JgraphyPostRequestDto jgraphyPostRequestDto = getJgraphyPostRequestDtoFixture();

    // expect
    this.mvc.perform(post("/jgraphy/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(jgraphyPostRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNumber());
  }

  @Test
  void createJgraphyPost_NullFields_BadRequest() throws Exception {
    // given
    JgraphyPostRequestDto jgraphyPostRequestDto = new JgraphyPostRequestDto();

    // expect
    this.mvc.perform(post("/jgraphy/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(jgraphyPostRequestDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void readJgraphyPosts_ValidInput_ValidOutput() throws Exception {
    // expect
    this.mvc.perform(get("/jgraphy/posts"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(10))
        .andExpect(jsonPath("$.content[9].id").value(1))
        .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
        .andExpect(jsonPath("$.pageable.pageSize").value(10));
  }

  @Test
  void getJgraphyPost_ValidInput_JgraphyPostResponseDto() throws Exception {
    this.mvc.perform(get("/jgraphy/posts/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("title1"))
        .andExpect(jsonPath("$.content").value("content1"))
        .andExpect(jsonPath("$.createdAt").isNotEmpty())
        .andExpect(jsonPath("$.updatedAt").isNotEmpty());
  }

  @Test
  void get_NonExistingId_ApiError() throws Exception {
    this.mvc.perform(get("/jgraphy/posts/{id}", Long.MAX_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void deleteJgraphyPost_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(delete("/jgraphy/posts/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());
  }

  @Test
  void updateNotice_ValidInput_ValidOutput() throws Exception {
    // given
    JgraphyPostRequestDto jgraphyPostRequestDto = getJgraphyPostRequestDtoFixture("updated title", "updated content");

    // expect
    this.mvc.perform(put("/jgraphy/posts/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(jgraphyPostRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());
  }
}
