package community.content.mjarticle.api.dto;

import community.content.mjarticle.domain.MjArticle;
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
  private ZonedDateTime updatedAt;

  MjArticleResponseDto(MjArticle mjArticle) {
    this.id = mjArticle.getId();
    this.title = mjArticle.getTitle();
    this.content = mjArticle.getContent();
    this.createdAt = mjArticle.getCreatedAt();
    this.updatedAt = mjArticle.getUpdatedAt();
  }

  public static MjArticleResponseDto of(MjArticle mjArticle) {
    return new MjArticleResponseDto(mjArticle);
  }
}
