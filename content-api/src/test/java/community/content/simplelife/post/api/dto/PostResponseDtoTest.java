package community.content.simplelife.post.api.dto;

import static community.content.simplelife.post.domain.PostTest.getPostFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.content.simplelife.post.domain.Post;
import org.junit.jupiter.api.Test;

public class PostResponseDtoTest {
  public static PostResponseDto getPostResponseFixture() throws Exception {
    return PostResponseDto.of(getPostFixture());
  }

  @Test
  void generatePostResponseFromPost_ValidInput_ReturnPostResponseDto() throws Exception {
    Post post = getPostFixture();
    PostResponseDto postResponseDto = PostResponseDto.of(post);

    then(postResponseDto)
        .hasFieldOrPropertyWithValue("id", post.getId())
        .hasFieldOrPropertyWithValue("title", post.getTitle())
        .hasFieldOrPropertyWithValue("description", post.getDescription())
        .hasFieldOrPropertyWithValue("content", post.getContent())
        .hasFieldOrPropertyWithValue("imageUrls", post.getImageUrls())
        .hasFieldOrPropertyWithValue("tags", post.getTags());
  }
}