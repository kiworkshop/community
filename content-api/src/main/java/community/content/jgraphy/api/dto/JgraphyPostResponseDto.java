package community.content.jgraphy.api.dto;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JgraphyPostResponseDto {
  private Long id;
  private String title;
  private String content;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  @Builder
  public JgraphyPostResponseDto(
      Long id, String title, String content,
      ZonedDateTime createdAt, ZonedDateTime updatedAt
  ) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
