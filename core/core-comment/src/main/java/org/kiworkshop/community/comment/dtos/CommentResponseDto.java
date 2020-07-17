package org.kiworkshop.community.comment.dtos;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
  private Long id;
  private String username;
  private String content;
  private Long parentId;
  private int order;
  private boolean active;
  private List<CommentResponseDto> children = new ArrayList<>();
  private ZonedDateTime createdAt;

  public void addChild(CommentResponseDto comment) {
    children.add(comment);
  }
}

