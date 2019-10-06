package community.content.mjarticle.api.dto;

import static community.content.mjarticle.domain.MjArticleTest.getMjArticleFixture;
import static org.assertj.core.api.BDDAssertions.then;

import community.content.mjarticle.domain.MjArticle;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MjArticleDetailResponseDtoTest {
  public static MjArticleDetailResponseDto getMjArticleDetailResponseDtoFixture() {
    MjArticle curr = getMjArticleFixture(2L);
    MjArticle prev = getMjArticleFixture(1L);
    MjArticle next = getMjArticleFixture(3L);
    ReflectionTestUtils.setField(prev, "title", "prevArticleTitle");
    ReflectionTestUtils.setField(next, "title", "nextArticleTitle");
    return  MjArticleDetailResponseDto.builder()
        .curr(curr).prev(prev).next(next).build();
  }

  @Test
  void construct_ValidInput_ValidOutput() {
    // given
    MjArticle curr = getMjArticleFixture(2L);
    MjArticle prev = getMjArticleFixture(1L);
    MjArticle next = getMjArticleFixture(3L);
    ReflectionTestUtils.setField(prev, "title", "prev title");
    ReflectionTestUtils.setField(next, "title", "next title");

    // when
    MjArticleDetailResponseDto responseDto = MjArticleDetailResponseDto.builder()
        .curr(curr).prev(prev).next(next).build();

    // then
    then(responseDto.getId()).isEqualTo(curr.getId());
    then(responseDto.getContent()).isEqualTo(curr.getContent());
    then(responseDto.getTitle()).isEqualTo(curr.getTitle());
    then(responseDto.getCreatedAt()).isEqualTo(curr.getCreatedAt());
    then(responseDto.getUpdatedAt()).isEqualTo(curr.getUpdatedAt());

    then(responseDto.getMeta())
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("prevArticleTitle", prev.getTitle())
        .hasFieldOrPropertyWithValue("nextArticleTitle", next.getTitle());
  }

  @Test
  void construct_prevIsNull_prevArticleTitleIsEmpty() {
    // given
    MjArticle curr = getMjArticleFixture(1L);
    MjArticle next = getMjArticleFixture(2L);
    ReflectionTestUtils.setField(next, "title", "next title");

    // when
    MjArticleDetailResponseDto responseDto = MjArticleDetailResponseDto.builder()
        .curr(curr).prev(null).next(next).build();

    // then
    then(responseDto.getMeta())
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("prevArticleTitle", "")
        .hasFieldOrPropertyWithValue("nextArticleTitle", next.getTitle());
  }

  @Test
  void construct_nextIsNull_prevArticleTitleIsEmpty() {
    // given
    MjArticle curr = getMjArticleFixture(2L);
    MjArticle prev = getMjArticleFixture(1L);
    ReflectionTestUtils.setField(prev, "title", "prev title");

    // when
    MjArticleDetailResponseDto responseDto = MjArticleDetailResponseDto.builder()
        .curr(curr).prev(prev).next(null).build();

    // then
    then(responseDto.getMeta())
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("prevArticleTitle", prev.getTitle())
        .hasFieldOrPropertyWithValue("nextArticleTitle", "");
  }
}
