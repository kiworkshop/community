package org.kiworkshop.community.content.simplelife.article.api.dto;

import static org.assertj.core.api.BDDAssertions.then;
import static org.kiworkshop.community.content.simplelife.article.domain.SimpleArticleTest.getArticleFixture;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleArticle;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleTag;

public class SimpleArticleResponseDtoTest {
  public static SimpleArticleResponseDto getPostResponseDtoFixture() throws Exception {
    return getPostResponseDtoFixture(1L);
  }

  public static SimpleArticleResponseDto getPostResponseDtoFixture(Long id) throws Exception {
    return SimpleArticleResponseDto.of(getArticleFixture(id));
  }

  @Test
  void generatePostResponseFromPost_ValidInput_ReturnPostResponseDto() throws Exception {
    SimpleArticle simpleArticle = getArticleFixture(1L);
    SimpleArticleResponseDto simpleArticleResponseDto = SimpleArticleResponseDto.of(simpleArticle);

    then(simpleArticleResponseDto)
        .hasFieldOrPropertyWithValue("id", simpleArticle.getId())
        .hasFieldOrPropertyWithValue("title", simpleArticle.getTitle())
        .hasFieldOrPropertyWithValue("description", simpleArticle.getDescription())
        .hasFieldOrPropertyWithValue("content", simpleArticle.getContent())
        .hasFieldOrPropertyWithValue("tags", simpleArticle.getSimpleTags().stream()
            .map(SimpleTag::getName).collect(Collectors.toSet()));
  }
}