package community.content.myanglog.api.dto;

import static community.content.myanglog.domain.TagTest.getTagFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.common.util.MyReflectionUtils;
import community.content.myanglog.domain.Tag;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class PostRequestDtoTest {
  private static Set<Tag> tagFixture = Set.of(getTagFixture());

  public static PostRequestDto getPostRequestDtoFixture() throws Exception {
    PostRequestDto postRequestDto = new PostRequestDto();

    MyReflectionUtils.setField(postRequestDto, "title", "title");
    MyReflectionUtils.setField(postRequestDto, "content", "content");
    MyReflectionUtils.setField(postRequestDto, "tags", tagFixture);

    return postRequestDto;
  }

  @Test
  void getPostResponseFromPost_ValidPost_ValidPostResponse() throws Exception {
    PostRequestDto postRequestDto = getPostRequestDtoFixture();

    then(postRequestDto)
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("tags", tagFixture);
  }
}
