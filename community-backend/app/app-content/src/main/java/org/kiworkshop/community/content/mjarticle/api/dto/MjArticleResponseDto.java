package org.kiworkshop.community.content.mjarticle.api.dto;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.content.mjarticle.domain.MjArticle;

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
