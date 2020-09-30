package org.kiworkshop.community.article.domain.service;

import java.security.Principal;

import org.kiworkshop.community.article.domain.exception.ArticleNotFoundException;
import org.kiworkshop.community.article.domain.model.Article;
import org.kiworkshop.community.article.domain.model.ArticleRepository;
import org.kiworkshop.community.article.dto.ArticleRequestDto;
import org.kiworkshop.community.article.dto.ArticleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Long create(ArticleRequestDto articleRequestDto) {
        Article article = articleRepository.save(ArticleConverter.toEntity(articleRequestDto));
        return article.getId();
    }

    public Page<ArticleResponseDto> readPage(Pageable pageable) {
        return articleRepository.findAllByActiveIsTrue(pageable).map(ArticleConverter::from);
    }

    public ArticleResponseDto read(Long id) {
        Article article = findById(id);
        return ArticleConverter.from(article);
    }

    public void update(Long id, ArticleRequestDto articleRequestDto) {
        Article article = findById(id);
        article.update(ArticleConverter.toEntity(articleRequestDto));
    }

    public void delete(Long id, Principal principal) {
        Article article = findById(id);
        article.deactivate(principal.getName());
        // TODO: 20. 8. 31. soft delete를 할지? hard delete를 할지 고민
        // if (!article.isAuthor(principal.getName())) {
        //     throw new IllegalArgumentException("unauthorized user");
        // }
        // articleRepository.delete(article);
    }

    private Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }
}
