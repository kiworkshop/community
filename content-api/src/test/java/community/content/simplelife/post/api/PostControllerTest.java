package community.content.simplelife.post.api;

import static community.content.simplelife.post.api.dto.PostRequestDtoTest.getPostRequestDtoFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.simplelife.post.api.dto.PostRequestDto;
import community.content.simplelife.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class PostControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean PostService postService;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void createPost_ValidInput_ValidOutput() throws Exception {
    // given
    PostRequestDto postRequestDto = getPostRequestDtoFixture();
    given(postService.createPost(any(PostRequestDto.class))).willReturn(1L);

    // expect
    this.mvc.perform(post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1L));
  }

  @Test
  void createPost_NullFields_StatusBadRequest() throws Exception {
    // given
    PostRequestDto postRequestDto = new PostRequestDto();

    // expect
    this.mvc.perform(post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postRequestDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void updatePost_ValidInput_ValidOutput() throws Exception {
    // given
    PostRequestDto postRequestDto = getPostRequestDtoFixture();

    // expect
    this.mvc.perform(put("/posts/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postRequestDto)))
        .andExpect(status().isOk());
  }

  @Test
  void deletePost_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(delete("/posts/{id}", 1))
        .andExpect(status().isOk());
  }
}