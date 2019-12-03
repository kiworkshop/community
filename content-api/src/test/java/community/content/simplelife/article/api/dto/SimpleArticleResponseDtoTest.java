package community.content.simplelife.article.api.dto;

import static community.content.simplelife.article.domain.SimpleArticleTest.getArticleFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.content.simplelife.article.domain.SimpleArticle;
import community.content.simplelife.article.domain.SimpleTag;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

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