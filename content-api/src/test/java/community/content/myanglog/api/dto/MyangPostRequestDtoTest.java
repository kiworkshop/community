package community.content.myanglog.api.dto;

import static community.content.myanglog.domain.TagTest.getTagFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import community.content.myanglog.domain.Tag;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MyangPostRequestDtoTest {
  private static Set<Tag> tagFixture = Set.of(getTagFixture());

  public static MyangPostRequestDto getMyangPostRequestDtoFixture() throws Exception {
    MyangPostRequestDto myangPostRequestDto = new MyangPostRequestDto();

    setField(myangPostRequestDto, "title", "title");
    setField(myangPostRequestDto, "content", "content");
    setField(myangPostRequestDto, "tags", tagFixture);

    return myangPostRequestDto;
  }

  public static MyangPostRequestDto getMyangPostRequestDtoFixture(String title, String content) throws Exception {
    MyangPostRequestDto myangPostRequestDto = new MyangPostRequestDto();

    ReflectionTestUtils.setField(myangPostRequestDto, "title", title);
    ReflectionTestUtils.setField(myangPostRequestDto, "content", content);

    return myangPostRequestDto;
  }

  @Test
  void getMyangPostResponseFromPost_ValidPost_ValidPostResponse() throws Exception {
    MyangPostRequestDto myangPostRequestDto = getMyangPostRequestDtoFixture();

    then(myangPostRequestDto)
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("tags", tagFixture);
  }
}
