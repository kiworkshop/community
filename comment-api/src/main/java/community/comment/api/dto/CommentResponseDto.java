package community.comment.api.dto;

import community.comment.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
  private Long id;
  private String username;
  private String content;
  private Long parentId;
  private int order;
  private boolean active;

  private CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.username = comment.getUsername();
    this.content = comment.getContent();
    this.parentId = comment.getParentId();
    this.active = comment.isActive();
    this.order = comment.getOrder();
  }

  public static CommentResponseDto of(Comment comment) {
    return new CommentResponseDto(comment);
  }
}

