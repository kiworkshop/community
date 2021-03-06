package org.kiworkshop.community.comment.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequestDto {
  @NotNull
  private BoardType boardType;

  @NotNull
  private Long postId;
  private Long parentId;
  private int ordinal;

  @NotEmpty
  private String username;

  @NotEmpty
  private String content;
}
