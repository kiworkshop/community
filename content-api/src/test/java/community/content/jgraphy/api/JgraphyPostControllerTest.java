package community.content.jgraphy.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.api.dto.JgraphyPostResponseDto;
import community.content.jgraphy.exception.JgraphyPostNotFoundException;
import community.content.jgraphy.service.JgraphyPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static community.content.jgraphy.api.dto.JgraphyPostRequestDtoTest.getJgraphyPostRequestDtoFixture;
import static community.content.jgraphy.api.dto.JgraphyPostResponseDtoTest.getJgraphyPostResponseDtoFixture;
import static community.content.jgraphy.domain.JgraphyPostTest.getJgraphyPostFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class JgraphyPostControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean JgraphyPostService jgraphyPostService;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void createJgraphyPost_validInput_validOutput() throws Exception{
    // given
    JgraphyPostRequestDto jgraphyPostRequestDto = getJgraphyPostRequestDtoFixture();
    given(jgraphyPostService.createPost(any(JgraphyPostRequestDto.class))).willReturn(1L);

    // expect
    mvc.perform(post("/jgraphy/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(jgraphyPostRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1L));

  }

  @Test
  void createJgraphyPost_nullFields_BadRequest() throws Exception {
    // given
    JgraphyPostRequestDto jgraphyPostRequestDto = new JgraphyPostRequestDto();

    // expect
    mvc.perform(post("/jgraphy/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(jgraphyPostRequestDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void readJgraphyPost_validInput_validOutput() throws Exception {
    // given
    JgraphyPostResponseDto jgraphyPostResponseDto = getJgraphyPostResponseDtoFixture();
    given(jgraphyPostService.readPost(anyLong())).willReturn(jgraphyPostResponseDto);

    // expect
    mvc.perform(get("/jgraphy/posts/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(jgraphyPostResponseDto.getId()))
        .andExpect(jsonPath("title").value(jgraphyPostResponseDto.getTitle()))
        .andExpect(jsonPath("content").value(jgraphyPostResponseDto.getContent()));
  }

  @Test
  void readJgraphyPost_NotExistingId_JgraphyPostNotFoundException() throws Exception {
    // given
    given(jgraphyPostService.readPost(anyLong())).willThrow(new JgraphyPostNotFoundException(1L));

    mvc.perform(get("/jgraphy/posts/{id}", 1L))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void deleteJgraphyPost_validInput_validOutput() throws Exception {
    mvc.perform(delete("/jgraphy/posts/{id}", 1L))
        .andExpect(status().isOk());
  }

  @Test
  void updateJgraphyPost_validInput_validOutput() throws Exception {
    JgraphyPostRequestDto jgraphyPostRequestDto = getJgraphyPostRequestDtoFixture();

    mvc.perform(put("/jgraphy/posts/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(jgraphyPostRequestDto)))
        .andExpect(status().isOk());
  }
}