package org.kiworkshop.community.article.domain.model;

import java.time.ZonedDateTime;

import org.springframework.test.util.ReflectionTestUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleFixture {
    public static Article get() {
        return get(1L);
    }

    public static Article get(Long id) {
        Article article = new Article();

        ReflectionTestUtils.setField(article, "id", id);
        ReflectionTestUtils.setField(article, "title", "title");
        ReflectionTestUtils.setField(article, "content", "content");
        ReflectionTestUtils.setField(article, "username", "username");
        ReflectionTestUtils.setField(article, "createdAt", ZonedDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", ZonedDateTime.now());

        return article;
    }
}
