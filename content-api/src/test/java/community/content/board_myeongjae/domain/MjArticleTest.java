package community.content.board_myeongjae.domain;

import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MjArticleTest {
  public static MjArticle getMjArticleFixture() {
    return getMjArticleFixture(1L);
  }

  public static MjArticle getMjArticleFixture(Long id) {
    MjArticle mjArticle = MjArticle.builder()
        .title("title")
        .content("content").build();

    ReflectionTestUtils.setField(mjArticle, "id", id);
    ReflectionTestUtils.setField(mjArticle, "createdAt", now());

    return mjArticle;
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // given
    MjArticle mjArticle = MjArticle.builder()
        .title("title")
        .content("content").build();

    // then
    then(mjArticle).hasNoNullFieldsOrPropertiesExcept("id", "createdAt");
    then(mjArticle.getTitle()).isEqualTo("title");
    then(mjArticle.getContent()).isEqualTo("content");
  }

  @Test
  void build_emptyTitle_ThrowException() {
    thenThrownBy(() -> MjArticle.builder().title("").content("content").build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void build_emptyContent_ThrowException() {
    thenThrownBy(() -> MjArticle.builder().title("title").content("").build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void update_ValidInput_ValidOutput() {
    // given
    MjArticle mjArticle = MjArticle.builder()
        .title("title")
        .content("content").build();

    // when
    mjArticle.updateMjArticle(MjArticle.builder()
        .title("updated title")
        .content("updated content").build());

    // then
    then(mjArticle).hasNoNullFieldsOrPropertiesExcept("id", "createdAt");
    then(mjArticle.getTitle()).isEqualTo("updated title");
    then(mjArticle.getContent()).isEqualTo("updated content");
  }
}
