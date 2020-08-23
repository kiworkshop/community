package org.kiworkshop.community.article.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.article.entity.Article;

@Getter
@NoArgsConstructor
public class ArticleRequestDto {
    private String title;

    @Builder
    public ArticleRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private String content;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
