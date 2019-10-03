package community.content.mjarticle.api.dto;

import static org.assertj.core.api.BDDAssertions.then;

import community.content.mjarticle.domain.MjArticle;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MjArticleRequestDtoTest {
  public static MjArticleRequestDto getMjArticleRequestDtoFixture() {
    MjArticleRequestDto mjArticleRequestDto = new MjArticleRequestDto();

    ReflectionTestUtils.setField(mjArticleRequestDto, "title", "title");
    ReflectionTestUtils.setField(mjArticleRequestDto, "content", "content");

    return mjArticleRequestDto;
  }

  public static MjArticleRequestDto getMjArticleRequestDtoFixture(String title, String content) {
    MjArticleRequestDto mjArticleRequestDto = new MjArticleRequestDto();

    ReflectionTestUtils.setField(mjArticleRequestDto, "title", title);
    ReflectionTestUtils.setField(mjArticleRequestDto, "content", content);

    return mjArticleRequestDto;
  }

  @Test
  void createDomain_ValidInput_ValidOutput() {
    // given
    MjArticleRequestDto request = getMjArticleRequestDtoFixture();

    // when
    MjArticle mjArticle = request.createDomain();

    // then
    then(mjArticle).hasNoNullFieldsOrPropertiesExcept("id", "createdAt");
    then(mjArticle.getTitle()).isEqualTo(request.getTitle());
    then(mjArticle.getContent()).isEqualTo(request.getContent());
  }
}

