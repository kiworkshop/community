package community.content.jgraphy.domain;

import community.common.util.MyReflectionUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class JgraphyPostTest {

  public static JgraphyPost getJgraphyPostFixture() throws Exception{
    return getJgraphyPostFixture(1L);
  }

  private static JgraphyPost getJgraphyPostFixture(long id) throws Exception{
    JgraphyPost jgraphyPost = JgraphyPost.builder().title("title").content("content").build();
    MyReflectionUtils.setField(jgraphyPost, "id", id);
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
}