package org.kiworkshop.community.article.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.article.entity.Article;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private String title;
    private String content;

    @Builder
    public ArticleResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static ArticleResponseDto from(Article article) {
        return ArticleResponseDto.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .build();
    }
}
