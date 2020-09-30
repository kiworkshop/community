package org.kiworkshop.community.article.domain.service;

import org.kiworkshop.community.article.domain.model.Article;
import org.kiworkshop.community.article.dto.ArticleRequestDto;
import org.kiworkshop.community.article.dto.ArticleResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleConverter {
    public static Article toEntity(ArticleRequestDto articleRequestDto) {
        return Article.builder()
            .title(articleRequestDto.getTitle())
            .content(articleRequestDto.getContent())
            .username(articleRequestDto.getUsername())
            .build();
    }

    public static ArticleResponseDto from(Article article) {
        return ArticleResponseDto.builder()
            .id(article.getId())
            .title(article.getTitle())
            .content(article.getContent())
            .username(article.getUsername())
            .createdAt(article.getCreatedAt())
            .updatedAt(article.getUpdatedAt())
            .build();
    }
}
