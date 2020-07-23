package org.kiworkshop.community.mother.comment.domain.model;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.kiworkshop.community.comment.dto.BoardType;

@Entity(name = "comments")
@Getter
@NoArgsConstructor
public class Comment {
  private static final String DELETED_COMMENT_MESSAGE = "삭제된 댓글입니다";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private BoardType boardType;

  private Long postId;

  private Long parentId;

  private int ordinal;

  private String username;

  private String content;

  private boolean active = true;

  @CreationTimestamp
  private ZonedDateTime createdAt;

  @Builder
  private Comment(
      @NonNull BoardType boardType,
      @NonNull Long postId,
      @NonNull Long parentId,
      int ordinal,
      @NonNull String username,
      @NonNull String content
  ) {
    this.boardType = boardType;
    this.postId = postId;
    this.parentId = parentId;
    this.ordinal = ordinal;
    this.username = username;
    this.content = content;
  }

  public void update(Comment dto) {
    this.boardType = dto.getBoardType();
    this.postId = dto.getPostId();
    this.parentId = dto.getParentId();
    this.content = dto.getContent();
  }

  public void deactivate() {
    this.active = false;
  }

  public String getContent() {
    if (this.active) {
      return DELETED_COMMENT_MESSAGE;
    }
    return this.content;
  }
}
