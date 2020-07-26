package org.kiworkshop.community.content.simplelife.article.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.kiworkshop.community.content.simplelife.article.api.dto.SimpleArticleRequestDto;
import org.kiworkshop.community.content.simplelife.article.api.dto.SimpleArticleRequestDtoTest;
import org.kiworkshop.community.content.simplelife.article.api.dto.SimpleArticleResponseDto;
import org.kiworkshop.community.content.simplelife.article.api.dto.SimpleArticleResponseDtoTest;
import org.kiworkshop.community.content.simplelife.article.exception.SimpleArticleNotFoundException;
import org.kiworkshop.community.content.simplelife.article.service.SimpleArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SimpleArticleController.class)
class SimpleArticleControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean SimpleArticleService simpleArticleService;
  private @Autowired ObjectMapper objectMapper;

  @Test
  void createPost_ValidInput_ValidOutput() throws Exception {
    // given
    SimpleArticleRequestDto simpleArticleRequestDto = SimpleArticleRequestDtoTest.getArticleRequestDtoFixture();
    given(simpleArticleService.createArticle(any(SimpleArticleRequestDto.class))).willReturn(1L);

    // expect
    this.mvc.perform(post("/simplelife/articles")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(simpleArticleRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1L));
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
    // given
    List<SimpleArticleResponseDto> simpleArticleResponseDtos = new ArrayList<>();
    final var numOfPosts = 10L;
    for (long i = 0; i < numOfPosts; i++) {
      simpleArticleResponseDtos.add(SimpleArticleResponseDtoTest.getPostResponseDtoFixture(i + 1));
    }
    Collections.reverse(simpleArticleResponseDtos);
    PageImpl<SimpleArticleResponseDto> postResponseDtoPage = new PageImpl<>(
        simpleArticleResponseDtos,
        PageRequest.of(0, 10, Sort.Direction.DESC, "id"),
        10);
    given(simpleArticleService.readArticlePage(any(Pageable.class))).willReturn(postResponseDtoPage);

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
    SimpleArticleResponseDto simpleArticleResponseDto = SimpleArticleResponseDtoTest.getPostResponseDtoFixture();
    given(simpleArticleService.readPost(anyLong())).willReturn(simpleArticleResponseDto);

    this.mvc.perform(get("/simplelife/articles/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(1L))
        .andExpect(jsonPath("title").value("title"))
        .andExpect(jsonPath("description").value("description"))
        .andExpect(jsonPath("content").value("content"));
  }

  @Test
  void getPost_NonExistentId_ApiError() throws Exception {
    given(simpleArticleService.readPost(anyLong())).willThrow(new SimpleArticleNotFoundException(1L));

    this.mvc.perform(get("/simplelife/articles/{id}", 1L))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void updatePost_ValidInput_ValidOutput() throws Exception {
    // given
    SimpleArticleRequestDto simpleArticleRequestDto = SimpleArticleRequestDtoTest.getArticleRequestDtoFixture();

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