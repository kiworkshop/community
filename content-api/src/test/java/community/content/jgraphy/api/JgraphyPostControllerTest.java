package community.content.jgraphy.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.domain.JgraphyPost;
import community.content.jgraphy.service.JgraphyPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static community.content.jgraphy.api.dto.JgraphyPostRequestDtoTest.getJgraphyPostRequestDtoFixture;
import static community.content.jgraphy.domain.JgraphyPostTest.getJgraphyPostFixture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
}