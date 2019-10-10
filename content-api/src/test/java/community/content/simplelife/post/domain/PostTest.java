package community.content.simplelife.post.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;

public class PostTest {
  public static Post getPostFixture() {
    return Post.builder()
        .title("title").description("description").content("content").build();
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // given
    Post post = Post.builder()
        .title("title")
        .description("description")
        .content("content").build();

    // then
    then(post).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt")
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("description", "description")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("viewCount", 0);
  }

  @Test
  void build_EmptyTitle_ThrowException() {
    // given
    thenThrownBy(() ->
        Post.builder()
            .title("")
            .description("description")
            .content("content").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }
}
