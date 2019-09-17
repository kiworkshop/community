package community.content.myanglog.api.dto;

import static community.content.myanglog.domain.PostTest.getPostFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.content.myanglog.domain.Post;
import org.junit.jupiter.api.Test;

public class PostResponseDtoTest {
  public static PostResponseDto getPostResponseFixture() throws Exception {
    return PostResponseDto.of(getPostFixture());
  }

  @Test
  void getPostResponseFromPost_ValidPost_ValidPostResponse() throws Exception {
    Post post = getPostFixture();
    PostResponseDto postResponseDto = PostResponseDto.of(post);

    then(postResponseDto)
        .hasNoNullFieldsOrPropertiesExcept("tags")
        .hasFieldOrPropertyWithValue("id", post.getId())
        .hasFieldOrPropertyWithValue("title", post.getTitle())
        .hasFieldOrPropertyWithValue("content", post.getContent());
  }
}
