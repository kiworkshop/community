package community.content.myanglog.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PostTest {
  public static Post getPostFixture() throws Exception {
    return getPostFixture(1L);
  }

  public static Post getPostFixture(Long id) throws Exception {
    Post post = Post.builder()
        .title("title")
        .content("content")
        .likeCount(0)
        .viewCount(0)
        .build();
    ReflectionTestUtils.setField(post, "id", id);
    return post;
  }

  @Test
  void build_ValidInput_ValidOutput() {
    Post post = Post.builder()
        .title("title")
        .content("content")
        .likeCount(0)
        .viewCount(0)
        .build();

    then(post)
        .hasNoNullFieldsOrPropertiesExcept("id", "tags")
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("likeCount", 0)
        .hasFieldOrPropertyWithValue("viewCount", 0);
  }

  @Test
  void build_EmptyContent_ThrowException() {
    thenThrownBy(() ->
          Post.builder()
              .title("title")
              .content("").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }
}