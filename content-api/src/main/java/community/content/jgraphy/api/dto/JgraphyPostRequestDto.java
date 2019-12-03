package community.content.jgraphy.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JgraphyPostRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
}
