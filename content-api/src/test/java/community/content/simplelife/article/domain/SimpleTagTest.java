package community.content.simplelife.article.domain;

import static community.content.simplelife.article.domain.SimpleArticleTest.getArticleFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class SimpleTagTest {

  public static Set<SimpleTag> getTagsFixture() {
    return getTagsFixture(Set.of("tag"));
  }

  public static Set<SimpleTag> getTagsFixture(Set<String> stringTags) {
    return stringTags.stream().map(tag -> SimpleTag.builder().name(tag).build()).collect(Collectors.toSet());
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // given
    SimpleTag simpleTag = SimpleTag.builder()
                .name("name").build();

    // then
    then(simpleTag).hasNoNullFieldsOrPropertiesExcept("id")
        .hasFieldOrPropertyWithValue("name", "name");
  }

  @Test
  void build_EmptyInput_ThrowException() {
    // given
    thenThrownBy(() ->
        SimpleTag.builder()
            .name("").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void add_ValidInput_ValidOutput() throws Exception {
    // given
    SimpleTag simpleTag = SimpleTag.builder().name("tag").build();
    SimpleArticle article = getArticleFixture(1L);

    // when
    simpleTag.addArticle(article);

    // then
    then(simpleTag.getSimpleArticles()).isEqualTo(Set.of(article));
  }
}