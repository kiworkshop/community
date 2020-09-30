package org.kiworkshop.community.article.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.article.domain.exception.ArticleNotFoundException;
import org.kiworkshop.community.article.domain.model.Article;
import org.kiworkshop.community.article.domain.model.ArticleFixture;
import org.kiworkshop.community.article.domain.model.ArticleRepository;
import org.kiworkshop.community.article.dto.ArticleRequestDto;
import org.kiworkshop.community.article.dto.ArticleRequestDtoFixture;
import org.kiworkshop.community.article.dto.ArticleResponseDto;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private Principal principal;
    private Article article;

    @BeforeEach
    void setUp() {
        this.articleService = new ArticleService(articleRepository);
        this.article = ArticleFixture.get();
    }

    @Test
    void create() {
        //given
        ArticleRequestDto articleRequestDto = ArticleRequestDtoFixture.get();
        given(articleRepository.save(any(Article.class))).willReturn(article);
        //when
        Long id = articleService.create(articleRequestDto);
        //then
        assertThat(id).isNotNull();
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    void readPage() {
        Long articleNum = 10L;
        // given
        List<Article> articles = LongStream.rangeClosed(1L, articleNum)
            .mapToObj(ArticleFixture::get)
            .collect(Collectors.toList());
        PageImpl<Article> articlePage = new PageImpl<>(articles);
        given(articleRepository.findAllByActiveIsTrue(any())).willReturn(articlePage);

        // when
        Pageable pageable = PageRequest.of(0, articleNum.intValue());
        Page<ArticleResponseDto> articleResponseDtoPage = articleService.readPage(pageable);

        // then
        assertThat(articleResponseDtoPage.getTotalElements()).isEqualTo(articleNum);
    }

    @Test
    void read() {
        //given
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
        ArticleRequestDto articleRequestDto = ArticleRequestDtoFixture.get();
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));
        //when
        articleService.update(1L, articleRequestDto);
        //then
        then(articleRepository).should().findById(anyLong());
    }

    @Test
    void update_throw_exception() {
        //given
        ArticleRequestDto articleRequestDto = ArticleRequestDtoFixture.get();
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));

        ReflectionTestUtils.setField(articleRequestDto, "username", "wrong username");
        //when & then
        assertThrows(IllegalArgumentException.class, () -> articleService.update(1L, articleRequestDto));
        then(articleRepository).should().findById(anyLong());
    }

    @Test
    void delete() {
        //given
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));
        given(principal.getName()).willReturn("username");
        //when
        articleService.delete(1L, principal);
        //then
        assertThat(article.isActive()).isEqualTo(false);
    }

    @Test
    void delete_throw_exception() {
        //given
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));
        given(principal.getName()).willReturn("username1");
        //when
        assertThrows(IllegalArgumentException.class, () -> articleService.delete(1L, principal));
        then(articleRepository).should().findById(anyLong());
    }

    @Test
    void findByIdThrowsException() {
        //given
        ArticleRequestDto articleRequestDto = ArticleRequestDtoFixture.get();
        //when & then
        assertThrows(ArticleNotFoundException.class, () -> articleService.read(1L));
        assertThrows(ArticleNotFoundException.class, () -> articleService.update(1L, articleRequestDto));
    }
}
