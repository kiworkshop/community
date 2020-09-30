package org.kiworkshop.community.article.dto;

import org.springframework.test.util.ReflectionTestUtils;

public class ArticleRequestDtoFixture {
    public static ArticleRequestDto get() {
        ArticleRequestDto articleRequestDto = new ArticleRequestDto();

        ReflectionTestUtils.setField(articleRequestDto, "title", "title");
        ReflectionTestUtils.setField(articleRequestDto, "content", "content");
        ReflectionTestUtils.setField(articleRequestDto, "username", "username");

        return articleRequestDto;
    }
}
