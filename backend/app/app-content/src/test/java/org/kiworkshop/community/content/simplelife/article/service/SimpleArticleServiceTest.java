package org.kiworkshop.community.content.simplelife.article.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.content.simplelife.article.api.dto.SimpleArticleRequestDto;
import org.kiworkshop.community.content.simplelife.article.api.dto.SimpleArticleRequestDtoTest;
import org.kiworkshop.community.content.simplelife.article.api.dto.SimpleArticleResponseDto;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleArticle;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleArticleRepository;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleArticleTest;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleTagRepository;
import org.kiworkshop.community.content.simplelife.article.exception.SimpleArticleNotFoundException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class SimpleArticleServiceTest {
  private SimpleArticleService simpleArticleService;
  private @Mock SimpleArticleRepository simpleArticleRepository;
  private @Mock SimpleTagRepository simpleTagRepository;

  @BeforeEach
  void setUp() {
    simpleArticleService = new SimpleArticleService(simpleArticleRepository, simpleTagRepository);
  }

  @Test
  void createPost_ValidInput_ValidOutput() throws Exception {
    // given
    SimpleArticleRequestDto simpleArticleRequestDto = SimpleArticleRequestDtoTest.getArticleRequestDtoFixture();

    SimpleArticle simpleArticleToSave = SimpleArticleTest.getArticleFixture(1L);
    given(simpleArticleRepository.save(any(SimpleArticle.class))).willReturn(simpleArticleToSave);

    // when
    Long id = simpleArticleService.createArticle(simpleArticleRequestDto);

    then(id).isEqualTo(simpleArticleToSave.getId());
  }

  @Test
  void readPost_ValidInput_ValidOutput() throws Exception {
    // given
    final Long numOfPosts = 10L;
    List<SimpleArticle> simpleArticles = new ArrayList<>();

    for (long i = 0; i < numOfPosts; i++) {
      simpleArticles.add(SimpleArticleTest.getArticleFixture(i));
    }

    PageImpl<SimpleArticle> postPage = new PageImpl<>(simpleArticles);
    given(simpleArticleRepository.findAll(any(Pageable.class))).willReturn(postPage);

    // when
    Page<SimpleArticleResponseDto> postResponseDtoPage = simpleArticleService.readArticlePage(
        PageRequest.of(0, numOfPosts.intValue()));

    // expect
    then(postResponseDtoPage.getTotalElements()).isEqualTo(numOfPosts);
  }

  @Test
  void readMjArticle_ValidInput_FoundMjArticle() throws Exception {
    SimpleArticle simpleArticle = SimpleArticleTest.getArticleFixture(1L);
    given(simpleArticleRepository.findById(anyLong())).willReturn(Optional.of(simpleArticle));

    SimpleArticleResponseDto foundPost = simpleArticleService.readPost(1L);

    then(foundPost)
        .hasFieldOrPropertyWithValue("id", simpleArticle.getId())
        .hasFieldOrPropertyWithValue("title", simpleArticle.getTitle())
        .hasFieldOrPropertyWithValue("content", simpleArticle.getContent());
  }

  @Test
  void readMjArticle_NonExistentMjArticleId_MjArticleNotFoundException() {
    given(simpleArticleRepository.findById(anyLong())).willReturn(Optional.empty());
    thenThrownBy(() -> simpleArticleService.readPost(1L)).isExactlyInstanceOf(SimpleArticleNotFoundException.class);
  }

  @Test
  void updatePost_validInput_validOutput() throws Exception {
    SimpleArticleRequestDto simpleArticleRequestDto = SimpleArticleRequestDtoTest.getArticleRequestDtoFixture();
    SimpleArticle simpleArticle = SimpleArticleTest.getArticleFixture(1L);
    given(simpleArticleRepository.findById(any(Long.class))).willReturn(Optional.of(simpleArticle));

    simpleArticleService.updateArticle(1L, simpleArticleRequestDto);
  }

  @Test
  void updatePost_nonExistMjArticle_throwException() throws Exception {
    SimpleArticleRequestDto postUpdatingRequestDto = SimpleArticleRequestDtoTest.getArticleRequestDtoFixture();
    given(simpleArticleRepository.findById(anyLong())).willReturn(Optional.empty());

    thenThrownBy(() -> simpleArticleService.updateArticle(1L, postUpdatingRequestDto))
        .isInstanceOf(SimpleArticleNotFoundException.class);
  }

  @Test
  void deletePost_ValidInput_DeleteMjArticle() throws Exception {
    // given
    SimpleArticle simpleArticleToDelete = SimpleArticleTest.getArticleFixture(1L);
    given(simpleArticleRepository.findById(any(Long.class))).willReturn(Optional.of(simpleArticleToDelete));

    // when
    simpleArticleService.deleteArticle(simpleArticleToDelete.getId());
  }

  @Test
  void deletePost_InvalidInput_Exception() {
    given(simpleArticleRepository.findById(any(Long.class))).willReturn(Optional.empty());
    thenThrownBy(() -> simpleArticleService.deleteArticle(1L))
        .isExactlyInstanceOf(SimpleArticleNotFoundException.class);
  }
}