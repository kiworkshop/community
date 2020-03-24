package community.comment.api.dto;

import community.common.model.BoardType;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CommentRequestDto {
  private BoardType boardType;
  private Long postId;
  private Long parentId;
  private int order;

  @NotEmpty
  private String username;

  @NotEmpty
  private String content;
}
