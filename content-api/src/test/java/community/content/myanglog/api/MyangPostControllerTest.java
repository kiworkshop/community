package community.content.myanglog.api;

import static community.content.myanglog.api.dto.MyangPostRequestDtoTest.getMyangPostRequestDtoFixture;
import static community.content.myanglog.api.dto.MyangPostResponseDtoTest.getMyangPostResponseFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.myanglog.api.dto.MyangPostRequestDto;
import community.content.myanglog.api.dto.MyangPostResponseDto;
import community.content.myanglog.exception.MyangPostNotFoundException;
import community.content.myanglog.service.MyangPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MyangPostController.class)
class MyangPostControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean MyangPostService service;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void readPost_ValidInput_ValidOutput() throws Exception {
    MyangPostResponseDto myangPostResponseDto = getMyangPostResponseFixture();
    given(service.readPost(anyLong())).willReturn(myangPostResponseDto);

    this.mvc.perform(get("/myanglog/posts/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(1L))
        .andExpect(jsonPath("title").value("title"))
        .andExpect(jsonPath("content").value("content"));
  }

  @Test
  void readPost_NonExistentIdInput_ApiError() throws Exception {
    given(service.readPost(anyLong())).willThrow(new MyangPostNotFoundException(1L));
    this.mvc.perform(get("/myanglog/posts/{id}", 1))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void create_ValidInput_ValidOutput() throws Exception {
    MyangPostRequestDto request = getMyangPostRequestDtoFixture();
    given(service.createPost(any(MyangPostRequestDto.class))).willReturn(1L);

    this.mvc.perform(post("/myanglog/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1L));
  }

  @Test
  void update_ValidInput_ValidOutput() throws Exception {
    MyangPostRequestDto myangPostRequestDto = getMyangPostRequestDtoFixture();

    this.mvc.perform(put("/myanglog/posts/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(myangPostRequestDto)))
        .andExpect(status().isOk());
  }

  @Test
  void delete_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(delete("/myanglog/posts/1"))
        .andExpect(status().isOk());
  }
}
