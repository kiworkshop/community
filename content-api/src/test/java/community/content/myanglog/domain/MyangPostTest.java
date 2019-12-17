package community.content.myanglog.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MyangPostTest {
  public static MyangPost getMyangPostFixture() throws Exception {
    return getMyangPostFixture(1L);
  }

  public static MyangPost getMyangPostFixture(Long id) {
    MyangPost myangPost = MyangPost.builder()
        .title("title")
        .content("content")
        .myangTags(new HashSet<>())
        .likeCount(0)
        .viewCount(0)
        .build();
    ReflectionTestUtils.setField(myangPost, "id", id);
    return myangPost;
  }

  @Test
  void build_ValidInput_ValidOutput() {
    MyangPost myangPost = MyangPost.builder()
        .title("title")
        .content("content")
        .likeCount(0)
        .viewCount(0)
        .build();

    then(myangPost)
        .hasNoNullFieldsOrPropertiesExcept("id", "myangTags")
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("likeCount", 0)
        .hasFieldOrPropertyWithValue("viewCount", 0);
  }

  @Test
  void build_EmptyContent_ThrowException() {
    thenThrownBy(() ->
          MyangPost.builder()
              .title("title")
              .content("").build()
    ).isInstanceOf(IllegalArgumentException.class);
  }
}