package community.content.simplelife.tag.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;

public class TagTest {
  public static Tag getTagFixture() {
    return Tag.builder().name("name").build();
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // given
    Tag tag = Tag.builder()
                .name("name").build();

    // then
    then(tag).hasNoNullFieldsOrPropertiesExcept("id")
        .hasFieldOrPropertyWithValue("name", "name");
  }

  @Test
  void build_EmptyInput_ThrowException() {
    // given
    thenThrownBy(() ->
        Tag.builder()
            .name("").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }
}