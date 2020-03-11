package community.comment.api.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CommentRequestDto {
  private Long boardId;
  private Long postId;
  private Long parentId;

  @NotEmpty
  private String userKey;

  @NotEmpty
  private String content;
}
