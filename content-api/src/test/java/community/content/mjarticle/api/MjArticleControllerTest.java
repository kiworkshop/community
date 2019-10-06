package community.content.mjarticle.api;

import static community.content.mjarticle.api.dto.MjArticleRequestDtoTest.getMjArticleRequestDtoFixture;
import static community.content.mjarticle.api.dto.MjArticleResponseDtoTest.getMjArticleResponseDtoFixture;
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
import community.content.mjarticle.api.dto.MjArticleRequestDto;
import community.content.mjarticle.api.dto.MjArticleResponseDto;
import community.content.mjarticle.exception.MjArticleNotFoundException;
import community.content.mjarticle.service.MjArticleService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MjArticleController.class)
class MjArticleControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean MjArticleService mjArticleService;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void createMjArticle_ValidInput_ValidOutput() throws Exception {
    // given
    MjArticleRequestDto mjArticleRequestDto = getMjArticleRequestDtoFixture();
    given(mjArticleService.createArticle(any(MjArticleRequestDto.class))).willReturn(1L);

    // expect
    this.mvc.perform(post("/myeongjae/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mjArticleRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1L));
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
    // given
    List<MjArticleResponseDto> mjArticleResponseDtos = new ArrayList<>();
    final var numMjArticles = 10L;
    for (long i = 0; i < numMjArticles; i++) {
      mjArticleResponseDtos.add(getMjArticleResponseDtoFixture(i + 1));
    }
    Collections.reverse(mjArticleResponseDtos);
    PageImpl<MjArticleResponseDto> mjArticleResponseDtoPage = new PageImpl<>(
        mjArticleResponseDtos,
        PageRequest.of(0, 10, Sort.Direction.DESC, "id"),
        10);
    given(mjArticleService.readArticlePage(any(Pageable.class))).willReturn(mjArticleResponseDtoPage);

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
    given(mjArticleService.readArticle(anyLong())).willReturn(mjArticleResponseDto);

    this.mvc.perform(get("/myeongjae/articles/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("title"))
        .andExpect(jsonPath("$.content").value("content"))
        .andExpect(jsonPath("$.createdAt").isString())
        .andExpect(jsonPath("$.updatedAt").isString());
  }

  @Test
  void get_NonExistentId_ApiError() throws Exception {
    given(mjArticleService.readArticle(anyLong())).willThrow(new MjArticleNotFoundException(1L));

    this.mvc.perform(get("/myeongjae/articles/{id}", 1))
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
