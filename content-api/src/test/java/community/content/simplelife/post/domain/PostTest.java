package community.content.simplelife.post.domain;

import static community.content.simplelife.tag.domain.TagTest.getTagFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import community.content.simplelife.tag.domain.Tag;

import java.util.Arrays;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class PostTest {
  public static Post getPostFixture() {
    return Post.builder()
        .title("title").description("description").content("content")
        .imageUrls(Arrays.asList("imageUrl1", "imageUrl2")).tags(Set.of(getTagFixture())).build();
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // given
    Set<Tag> tags = Set.of(getTagFixture());
    Post post = Post.builder()
        .title("title")
        .description("description")
        .content("content")
        .imageUrls(Arrays.asList("imageUrl1", "imageUrl2"))
        .tags(tags).build();

    // then
    then(post).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt")
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("description", "description")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("imageUrls", Arrays.asList("imageUrl1", "imageUrl2"))
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
