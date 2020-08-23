package org.kiworkshop.community.article.service;

import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.article.api.dto.ArticleRequestDto;
import org.kiworkshop.community.article.api.dto.ArticleResponseDto;
import org.kiworkshop.community.article.entity.Article;
import org.kiworkshop.community.article.entity.ArticleRepository;
import org.kiworkshop.community.article.exception.ArticleException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Long create(ArticleRequestDto articleRequestDto) {
        Article article = articleRepository.save(articleRequestDto.toEntity());
        return article.getId();
    }

    public ArticleResponseDto read(Long id) {
        Article article = findById(id);
        return ArticleResponseDto.from(article);
    }

    public void update(Long id, ArticleRequestDto articleRequestDto) {
        Article article = findById(id);
        article.update(articleRequestDto.toEntity());
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    private Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleException("게시물을 찾을 수 없습니다."));
    }
}
