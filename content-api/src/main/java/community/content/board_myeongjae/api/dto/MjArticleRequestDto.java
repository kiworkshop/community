package community.content.board_myeongjae.api.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MjArticleRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
}
