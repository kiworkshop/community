package org.kiworkshop.community.content.mjarticle.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.content.mjarticle.domain.MjArticle;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
public class MjArticleDetailResponseDto extends MjArticleResponseDto {
  @Getter
  @NoArgsConstructor
  private class Meta {
    private String prevArticleTitle;
    private String nextArticleTitle;
  }

  private Meta meta;

  @Builder
  private MjArticleDetailResponseDto(MjArticle curr, @Nullable MjArticle prev, @Nullable MjArticle next) {
    super(curr);

    meta = new Meta();
    meta.prevArticleTitle = prev == null ? "" : prev.getTitle();
    meta.nextArticleTitle = next == null ? "" : next.getTitle();
  }
}
