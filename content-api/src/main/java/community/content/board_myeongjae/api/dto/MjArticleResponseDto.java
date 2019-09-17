package community.content.board_myeongjae.api.dto;

import community.content.board_myeongjae.domain.MjArticle;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MjArticleResponseDto {
  private Long id;
  private String title;
  private String content;
  private ZonedDateTime createdAt;

  private MjArticleResponseDto(MjArticle mjArticle) {
    this.id = mjArticle.getId();
    this.title = mjArticle.getTitle();
    this.content = mjArticle.getContent();
    this.createdAt = mjArticle.getCreatedAt();
  }

  public static MjArticleResponseDto of(MjArticle mjArticle) {
    return new MjArticleResponseDto(mjArticle);
  }
}
