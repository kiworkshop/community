package community.content.myanglog.api.dto;

import static community.content.myanglog.domain.TagTest.getTagFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.content.myanglog.domain.Tag;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PostRequestDtoTest {
  private static Set<Tag> tagFixture = Set.of(getTagFixture());

  public static PostRequestDto getPostRequestDtoFixture() throws Exception {
    PostRequestDto postRequestDto = new PostRequestDto();

    ReflectionTestUtils.setField(postRequestDto, "title", "title");
    ReflectionTestUtils.setField(postRequestDto, "content", "content");
    ReflectionTestUtils.setField(postRequestDto, "tags", tagFixture);

    return postRequestDto;
  }

  public static PostRequestDto getPostRequestDtoFixture(String title, String content) throws Exception {
    PostRequestDto postRequestDto = new PostRequestDto();

    MyReflectionUtils.setField(postRequestDto, "title", title);
    MyReflectionUtils.setField(postRequestDto, "content", content);

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
