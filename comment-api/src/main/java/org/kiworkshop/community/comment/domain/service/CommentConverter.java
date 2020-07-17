package org.kiworkshop.community.comment.domain.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.comment.domain.Comment;
import org.kiworkshop.community.comment.dtos.CommentRequestDto;
import org.kiworkshop.community.comment.dtos.CommentResponseDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentConverter {
  public static Comment toEntity(CommentRequestDto dto) {
    return Comment.builder()
        .boardType(dto.getBoardType())
        .content(dto.getContent())
        .parentId(dto.getParentId())
        .postId(dto.getPostId())
        .order(dto.getOrder())
        .username(dto.getUsername())
        .build();
  }

  public static CommentResponseDto toResponseDto(Comment comment) {
    return CommentResponseDto.builder()
        .id(comment.getId())
        .username(comment.getUsername())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt())
        .order(comment.getOrder())
        .parentId(comment.getParentId())
        .active(comment.isActive())
        .build();
  }
}
