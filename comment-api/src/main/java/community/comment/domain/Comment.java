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

  private String userKey;

  private String content;

  private boolean active = true;

  private Comment(CommentRequestDto requestDto) {
    this.boardId = requestDto.getBoardId();
    this.postId = requestDto.getPostId();
    this.parentId = requestDto.getParentId();
    this.userKey = requestDto.getUserKey();
    this.content = requestDto.getContent();
  }

  public static Comment from(CommentRequestDto requestDto) {
    return new Comment(requestDto);
  }

  public void deactivate() {
    this.active = false;
  }
}
