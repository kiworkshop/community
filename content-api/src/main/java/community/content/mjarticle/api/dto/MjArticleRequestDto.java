package community.content.mjarticle.api.dto;

import community.content.mjarticle.domain.MjArticle;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MjArticleRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;

  public MjArticle createDomain() {
    return MjArticle.builder()
        .title(title)
        .content(content).build();
  }
}
