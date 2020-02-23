package community.content.myanglog.api.dto;

import static community.content.myanglog.domain.MyangPostTest.getMyangPostFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.content.myanglog.domain.MyangPost;
import org.junit.jupiter.api.Test;

public class MyangPostResponseDtoTest {
  public static MyangPostResponseDto getMyangPostResponseFixture() throws Exception {
    return MyangPostResponseDto.from(getMyangPostFixture());
  }

  @Test
  void getMyangPostResponseFromPost_ValidPost_ValidPostResponse() throws Exception {
    MyangPost myangPost = getMyangPostFixture();
    MyangPostResponseDto myangPostResponseDto = MyangPostResponseDto.from(myangPost);

    then(myangPostResponseDto)
        .hasNoNullFieldsOrPropertiesExcept("myangTags")
        .hasFieldOrPropertyWithValue("id", myangPost.getId())
        .hasFieldOrPropertyWithValue("title", myangPost.getTitle())
        .hasFieldOrPropertyWithValue("content", myangPost.getContent());
  }
}
