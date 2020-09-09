package org.kiworkshop.community.article.dto;

import java.time.ZonedDateTime;

public class ArticleResponseDtoFixture {
    public static ArticleResponseDto get() {
        return ArticleResponseDto.builder()
            .id(1L)
            .title("title")
            .content("content")
            .username("username")
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();
    }
}
