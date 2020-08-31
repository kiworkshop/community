package org.kiworkshop.community.article.service;

import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.article.api.dto.ArticleRequestDto;
import org.kiworkshop.community.article.api.dto.ArticleResponseDto;
import org.kiworkshop.community.article.entity.Article;
import org.kiworkshop.community.article.entity.ArticleRepository;
import org.kiworkshop.community.article.exception.ArticleException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Long create(ArticleRequestDto articleRequestDto, Principal principal) {
        Article article = articleRepository.save(articleRequestDto.toEntity(principal.getName()));
        return article.getId();
    }

    public ArticleResponseDto read(Long id) {
        Article article = findById(id);
        return ArticleResponseDto.from(article);
    }

    public void update(Long id, ArticleRequestDto articleRequestDto, Principal principal) {
        Article article = findById(id);
        article.update(articleRequestDto.toEntity(principal.getName()));
    }

    public void delete(Long id, Principal principal) {
        Article article = findById(id);
        // TODO: 20. 8. 31. soft delete를 할지? hard delete를 할지 고민
        if (!article.isAuthor(principal.getName())) {
            throw new IllegalArgumentException("unauthorized user");
        }
        articleRepository.delete(article);
    }

    private Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleException("게시물을 찾을 수 없습니다."));
    }
}
