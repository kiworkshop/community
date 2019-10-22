package community.content.jgraphy.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class JgraphyPostRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
}
