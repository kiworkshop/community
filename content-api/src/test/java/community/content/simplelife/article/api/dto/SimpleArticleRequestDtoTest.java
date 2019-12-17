package community.content.simplelife.article.api.dto;

import community.common.util.MyReflectionUtils;
import java.util.Set;

public class SimpleArticleRequestDtoTest {

  public static SimpleArticleRequestDto getArticleRequestDtoFixture() throws Exception {
    SimpleArticleRequestDto simpleArticleRequestDto = new SimpleArticleRequestDto();
    Set<String> tags = Set.of("tag");

    MyReflectionUtils.setField(simpleArticleRequestDto, "title", "title");
    MyReflectionUtils.setField(simpleArticleRequestDto, "description", "description");
    MyReflectionUtils.setField(simpleArticleRequestDto, "content", "content");
    MyReflectionUtils.setField(simpleArticleRequestDto, "simpleTags", tags);

    return simpleArticleRequestDto;
  }
}