package community.content.board_myeongjae.api.dto;

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
}

