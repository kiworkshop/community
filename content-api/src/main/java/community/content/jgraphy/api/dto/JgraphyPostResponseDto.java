package community.content.jgraphy.api.dto;

import community.content.jgraphy.domain.JgraphyPost;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JgraphyPostResponseDto {
  private Long id;
  private String title;
  private String content;

  JgraphyPostResponseDto(JgraphyPost jgraphyPost) {
    this.id = jgraphyPost.getId();
    this.title = jgraphyPost.getTitle();
    this.content = jgraphyPost.getContent();
  }

  public static JgraphyPostResponseDto of(JgraphyPost jgraphyPost) {
    return new JgraphyPostResponseDto(jgraphyPost);
  }

}
