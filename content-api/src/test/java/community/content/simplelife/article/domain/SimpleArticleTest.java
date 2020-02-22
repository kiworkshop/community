package community.content.simplelife.article.domain;

import static community.content.simplelife.article.domain.SimpleTagTest.getTagsFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class SimpleArticleTest {
  public static SimpleArticle getArticleFixture(Long id) throws Exception {
    Set<SimpleTag> simpleTags = getTagsFixture();
    SimpleArticle simpleArticle = SimpleArticle.builder()
        .title("title").description("description").content("content").simpleTags(simpleTags).build();
    ReflectionTestUtils.setField(simpleArticle, "id", id);
    return simpleArticle;
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // given
    Set<SimpleTag> simpleTags = getTagsFixture();
    SimpleArticle simpleArticle = SimpleArticle.builder()
        .title("title")
        .description("description")
        .content("content")
        .simpleTags(simpleTags).build();

    // then
    then(simpleArticle).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt")
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("description", "description")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("simpleTags", simpleTags);
  }

  @Test
  void build_EmptyTitle_ThrowException() {
    // given
    thenThrownBy(() ->
        SimpleArticle.builder()
            .title("")
            .description("description")
            .content("content").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void update_ValidInput_ValidOutput() {
    // given
    Set<SimpleTag> simpleTags = getTagsFixture(Set.of("tag"));
    Set<SimpleTag> newSimpleTags = getTagsFixture(Set.of("newTag"));

    SimpleArticle simpleArticle = SimpleArticle.builder()
        .title("title")
        .description("description")
        .content("content")
        .simpleTags(simpleTags).build();

    SimpleArticle articleToUpdate = SimpleArticle.builder()
        .title("updated title")
        .description("updated description")
        .content("updated content").build();

    // when
    simpleArticle.update(articleToUpdate, newSimpleTags);

    // then
    simpleTags.addAll(newSimpleTags);
    then(simpleArticle).hasNoNullFieldsOrPropertiesExcept("id","createdAt", "updatedAt");
    then(simpleArticle.getTitle()).isEqualTo("updated title");
    then(simpleArticle.getDescription()).isEqualTo("updated description");
    then(simpleArticle.getContent()).isEqualTo("updated content");
    then(simpleArticle.getSimpleTags()).isEqualTo(newSimpleTags);
  }

  @Test
  void add_ValidInput_ValidOutput() {
    // given
    Set<SimpleTag> tags = getTagsFixture(Set.of("tag"));

    SimpleArticle article = SimpleArticle.builder()
        .title("title").description("description").content("content").simpleTags(new HashSet<>()).build();

    // when
    article.addTags(tags);

    // then
    then(article.getSimpleTags()).isEqualTo(tags);
    for (SimpleTag tag: tags) {
      then(tag.getSimpleArticles()).isEqualTo(Set.of(article));
    }
  }
}
