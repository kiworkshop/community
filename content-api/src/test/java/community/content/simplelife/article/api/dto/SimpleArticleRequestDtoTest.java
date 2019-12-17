package community.content.simplelife.article.api.dto;

import java.util.Set;
import org.springframework.test.util.ReflectionTestUtils;

public class SimpleArticleRequestDtoTest {

  public static SimpleArticleRequestDto getArticleRequestDtoFixture() throws Exception {
    SimpleArticleRequestDto simpleArticleRequestDto = new SimpleArticleRequestDto();
    Set<String> tags = Set.of("tag");

    ReflectionTestUtils.setField(simpleArticleRequestDto, "title", "title");
    ReflectionTestUtils.setField(simpleArticleRequestDto, "description", "description");
    ReflectionTestUtils.setField(simpleArticleRequestDto, "content", "content");
    ReflectionTestUtils.setField(simpleArticleRequestDto, "simpleTags", tags);

    return simpleArticleRequestDto;
  }
}