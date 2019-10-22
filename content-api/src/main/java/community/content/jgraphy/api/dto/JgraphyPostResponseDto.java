package community.content.jgraphy.api.dto;

import community.content.jgraphy.domain.JgraphyPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JgraphyPostResponseDto {
  private Long id;
  private String title;
  private String content;

  @Builder
  public JgraphyPostResponseDto(Long id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }
}
