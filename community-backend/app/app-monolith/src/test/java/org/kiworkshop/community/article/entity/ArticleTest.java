package org.kiworkshop.community.article.entity;

import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;

public class ArticleTest {
    public static Article createArticleTestFixture() {
        Article article = Article.builder().title("title").content("content").username("username").build();
        ReflectionTestUtils.setField(article, "id", 1L);
        ReflectionTestUtils.setField(article, "updatedAt", ZonedDateTime.now());
        ReflectionTestUtils.setField(article, "createdAt", ZonedDateTime.now());
        return article;
    }
}