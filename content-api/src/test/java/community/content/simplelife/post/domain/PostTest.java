package community.content.simplelife.post.domain;

import static community.content.simplelife.tag.domain.TagTest.getTagFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import community.content.simplelife.tag.domain.Tag;

import java.util.Set;
import org.junit.jupiter.api.Test;

public class PostTest {
  @Test
  void build_ValidInput_ValidOutput() {
    // given
    Set<Tag> tags = Set.of(getTagFixture());
    Post post = Post.builder()
        .title("title")
        .description("description")
        .content("content")
        .tags(tags).build();

    // then
    then(post).hasNoNullFieldsOrPropertiesExcept("id", "createdAt")
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("description", "description")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("tags", tags);
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
