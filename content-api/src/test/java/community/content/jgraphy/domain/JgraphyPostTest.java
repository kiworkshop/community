package community.content.jgraphy.domain;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class JgraphyPostTest {

  public static JgraphyPost getJgraphyPostFixture() {
    return getJgraphyPostFixture(1L);
  }

  public static JgraphyPost getJgraphyPostFixture(long id) {
    JgraphyPost jgraphyPost = JgraphyPost.builder().title("title").content("content").build();
    ReflectionTestUtils.setField(jgraphyPost, "id", id);
    return jgraphyPost;
  }

  @Test
  void build_validInput_validOutput() {
    JgraphyPost jgraphyPost = JgraphyPost.builder()
        .title("title")
        .content("content")
        .build();

    //then
    then(jgraphyPost)
        .hasNoNullFieldsOrPropertiesExcept("id")
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("content", "content");
  }

  @Test
  void build_EmptyTitleorContent_ThrowsException() {
    thenThrownBy(() ->
        JgraphyPost.builder()
          .title("")
          .content("content")
          .build()
    ).isInstanceOf(IllegalArgumentException.class);

    thenThrownBy(() ->
        JgraphyPost.builder()
          .title("title")
          .content("")
          .build()
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void updateEntity_validInput_validOutput() {
    JgraphyPost jgraphyPost = JgraphyPost.builder()
        .title("title")
        .content("content")
        .build();

    JgraphyPost newJgraphyPost = JgraphyPost.builder()
        .title("new title")
        .content("new content")
        .build();

    jgraphyPost.update(newJgraphyPost);

    then(jgraphyPost)
        .hasNoNullFieldsOrPropertiesExcept("id")
        .hasFieldOrPropertyWithValue("title", "new title")
        .hasFieldOrPropertyWithValue("content", "new content");
  }
}