package community.content.myanglog.api;

import static community.content.myanglog.api.dto.MyangPostRequestDtoTest.getMyangPostRequestDtoFixture;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.ContentApiApplication;
import community.content.myanglog.api.dto.MyangPostRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest(classes = ContentApiApplication.class, webEnvironment = RANDOM_PORT)
@Transactional
@Sql("/data/posts.sql")
public class MyangPostControllerIntTest {
  private @Autowired MockMvc mvc;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void create_ValidInput_ValidOutput() throws Exception {
    MyangPostRequestDto myangPostRequestDto = getMyangPostRequestDtoFixture();

    MvcResult result = this.mvc.perform(post("/myanglog/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(myangPostRequestDto)))
        .andExpect(status().isOk())
        .andReturn();

    String id = result.getResponse().getContentAsString();

    this.mvc.perform(get("/myanglog/posts/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("title"))
        .andExpect(jsonPath("$.myangTags[0].name").value("tagName"));
  }

  @Test
  void update_ValidInput_ValidOutput() throws Exception {
    String updatedTitle = "updated title";
    String updatedContent = "updated content";
    MyangPostRequestDto myangPostRequestDto = getMyangPostRequestDtoFixture(updatedTitle, updatedContent);

    this.mvc.perform(put("/myanglog/posts/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(myangPostRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());

    this.mvc.perform(get("/myanglog/posts/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value(updatedTitle))
        .andExpect(jsonPath("$.content").value(updatedContent));
  }

  @Test
  void delete_ValidInput_PostNotFoundAfterDeletion() throws Exception {
    this.mvc.perform(delete("/myanglog/posts/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());

    this.mvc.perform(get("/myanglog/posts/1"))
        .andExpect(status().isNotFound());
  }
}
