package community.content.myanglog.api;

import static community.content.myanglog.api.dto.PostRequestDtoTest.getPostRequestDtoFixture;
import static community.content.myanglog.api.dto.PostResponseDtoTest.getPostResponseFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.myanglog.api.dto.PostRequestDto;
import community.content.myanglog.api.dto.PostResponseDto;
import community.content.myanglog.exception.PostNotFoundException;
import community.content.myanglog.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostController.class)
class PostControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean PostService service;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void readPost_ValidInput_ValidOutput() throws Exception {
    PostResponseDto postResponseDto = getPostResponseFixture();
    given(service.readPost(anyLong())).willReturn(postResponseDto);

    this.mvc.perform(get("/myanglog/posts/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(1L))
        .andExpect(jsonPath("title").value("title"))
        .andExpect(jsonPath("content").value("content"));
  }

  @Test
  void readPost_NonExistentIdInput_ApiError() throws Exception {
    given(service.readPost(anyLong())).willThrow(new PostNotFoundException(1L));
    this.mvc.perform(get("/myanglog/posts/{id}", 1))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void create_ValidInput_ValidOutput() throws Exception {
    PostRequestDto request = getPostRequestDtoFixture();
    given(service.createPost(any(PostRequestDto.class))).willReturn(1L);

    this.mvc.perform(post("/myanglog/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1L));
  }
}
