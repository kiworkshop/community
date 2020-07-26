package org.kiworkshop.community.content.myanglog.api.dto;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.kiworkshop.community.content.myanglog.domain.MyangTag;
import org.kiworkshop.community.content.myanglog.domain.MyangTagTest;

public class MyangPostRequestDtoTest {
  private static Set<MyangTag> myangTagFixture = Set.of(MyangTagTest.getTagFixture());

  public static MyangPostRequestDto getMyangPostRequestDtoFixture() throws Exception {
    MyangPostRequestDto myangPostRequestDto = new MyangPostRequestDto();

    setField(myangPostRequestDto, "title", "title");
    setField(myangPostRequestDto, "content", "content");
    setField(myangPostRequestDto, "myangTags", myangTagFixture);

    return myangPostRequestDto;
  }

  public static MyangPostRequestDto getMyangPostRequestDtoFixture(String title, String content) throws Exception {
    MyangPostRequestDto myangPostRequestDto = new MyangPostRequestDto();

    setField(myangPostRequestDto, "title", title);
    setField(myangPostRequestDto, "content", content);

    return myangPostRequestDto;
  }

  @Test
  void getMyangPostResponseFromPost_ValidPost_ValidPostResponse() throws Exception {
    MyangPostRequestDto myangPostRequestDto = getMyangPostRequestDtoFixture();

    then(myangPostRequestDto)
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("title", "title")
        .hasFieldOrPropertyWithValue("content", "content")
        .hasFieldOrPropertyWithValue("myangTags", myangTagFixture);
  }
}
