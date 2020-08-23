package org.kiworkshop.community.article.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.article.api.dto.ArticleRequestDto;
import org.kiworkshop.community.article.api.dto.ArticleResponseDto;
import org.kiworkshop.community.article.entity.Article;
import org.kiworkshop.community.article.entity.ArticleRepository;
import org.kiworkshop.community.article.exception.ArticleException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.kiworkshop.community.article.entity.ArticleTest.createArticleTestFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        this.articleService = new ArticleService(articleRepository);
    }

    @Test
    void create() {
        //given
        ArticleRequestDto articleRequestDto = ArticleRequestDto.builder().title("title").content("content").build();
        Article article = createArticleTestFixture();
        given(articleRepository.save(any(Article.class))).willReturn(article);
        //when
        Long id = articleService.create(articleRequestDto);
        //then
        assertThat(id).isNotNull();
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    void read() {
        //given
        Article article = createArticleTestFixture();
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));
        //when
        ArticleResponseDto articleResponseDto = articleService.read(1L);
        //then
        assertThat(articleResponseDto.getTitle()).isEqualTo(article.getTitle());
        assertThat(articleResponseDto.getContent()).isEqualTo(article.getContent());
        then(articleRepository).should().findById(anyLong());
    }

    @Test
    void update() {
        //given
        ArticleRequestDto articleRequestDto = ArticleRequestDto.builder().title("title1").content("content1").build();
        Article article = createArticleTestFixture();
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));
        //when
        articleService.update(1L, articleRequestDto);
        //then
        then(articleRepository).should().findById(anyLong());
    }

    @Test
    void delete() {
        //when
        articleService.delete(1L);
        //then
        then(articleRepository).should().deleteById(anyLong());
    }

    @Test
    void findByIdThrowsException() {
        //given
        ArticleRequestDto articleRequestDto = ArticleRequestDto.builder().title("title1").content("content1").build();
        //when & then
        assertThrows(ArticleException.class, () -> articleService.read(1L));
        assertThrows(ArticleException.class, () -> articleService.update(1L, articleRequestDto));
    }
}