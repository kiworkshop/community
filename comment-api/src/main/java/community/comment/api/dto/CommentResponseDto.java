package community.comment.api.dto;

import community.comment.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
  private Long id;
  private String userKey;
  private String content;
  private Long parentId;
  private boolean active;

  private CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.userKey = comment.getUserKey();
    this.content = comment.getContent();
    this.parentId = comment.getParentId();
    this.active = comment.isActive();
  }

  public static CommentResponseDto of(Comment comment) {
    return new CommentResponseDto(comment);
  }
}

