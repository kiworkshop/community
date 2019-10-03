package community.content.board_myeongjae.api.dto;

import community.content.board_myeongjae.domain.MjArticle;
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
