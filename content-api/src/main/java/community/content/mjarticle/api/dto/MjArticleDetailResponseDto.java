package community.content.mjarticle.api.dto;

import community.content.mjarticle.domain.MjArticle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
public class MjArticleDetailResponseDto extends MjArticleResponseDto {
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
