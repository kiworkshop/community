package community.comment.domain;

import community.comment.api.dto.CommentRequestDto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long boardId;

  private Long postId;

  private Long parentId;

  private int order;

  private String username;

  private String content;

  private boolean active = true;

  private Comment(CommentRequestDto requestDto) {
    this.boardId = requestDto.getBoardId();
    this.postId = requestDto.getPostId();
    this.parentId = requestDto.getParentId();
    this.username = requestDto.getUsername();
    this.content = requestDto.getContent();
    this.order = requestDto.getOrder();
  }

  public static Comment from(CommentRequestDto requestDto) {
    return new Comment(requestDto);
  }

  public void update(CommentRequestDto requestDto) {
    this.boardId = requestDto.getBoardId();
    this.postId = requestDto.getPostId();
    this.parentId = requestDto.getParentId();
    this.content = requestDto.getContent();
  }

  public void deactivate() {
    this.active = false;
  }
}
