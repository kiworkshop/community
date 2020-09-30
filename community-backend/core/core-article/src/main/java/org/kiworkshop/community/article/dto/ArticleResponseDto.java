package org.kiworkshop.community.article.dto;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Builder
    private ArticleResponseDto(
        Long id,
        String title,
        String content,
        String username,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.content =content;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
