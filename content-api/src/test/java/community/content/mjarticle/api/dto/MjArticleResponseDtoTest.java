package community.content.mjarticle.api.dto;

import static community.content.mjarticle.domain.MjArticleTest.getMjArticleFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.content.mjarticle.domain.MjArticle;
import org.junit.jupiter.api.Test;

public class MjArticleResponseDtoTest {
  public static MjArticleResponseDto getMjArticleResponseDtoFixture() {
    return getMjArticleResponseDtoFixture(1L);
  }

  public static MjArticleResponseDto getMjArticleResponseDtoFixture(Long id) {
    return MjArticleResponseDto.of(getMjArticleFixture(id));
  }

  @Test
  void construct_ValidInput_ValidOutput() {
    // given
    MjArticle mjArticle = getMjArticleFixture();

    // when
    MjArticleResponseDto mjArticleResponseDto = MjArticleResponseDto.of(mjArticle);

    // then
    then(mjArticleResponseDto).hasNoNullFieldsOrProperties();
    then(mjArticleResponseDto.getId()).isEqualTo(mjArticle.getId());
    then(mjArticleResponseDto.getTitle()).isEqualTo(mjArticle.getTitle());
    then(mjArticleResponseDto.getContent()).isEqualTo(mjArticle.getContent());
    then(mjArticleResponseDto.getCreatedAt()).isEqualTo(mjArticle.getCreatedAt());
    then(mjArticleResponseDto.getUpdatedAt()).isEqualTo(mjArticle.getUpdatedAt());
  }
}
