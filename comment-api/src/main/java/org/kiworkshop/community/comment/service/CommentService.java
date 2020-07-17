package org.kiworkshop.community.comment.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.comment.domain.Comment;
import org.kiworkshop.community.comment.domain.CommentRepository;
import org.kiworkshop.community.comment.domain.service.CommentConverter;
import org.kiworkshop.community.comment.dtos.BoardType;
import org.kiworkshop.community.comment.dtos.CommentRequestDto;
import org.kiworkshop.community.comment.dtos.CommentResponseDto;
import org.kiworkshop.community.comment.exception.CommentNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;

  public List<CommentResponseDto> getComments(BoardType boardType, Long postId) {
    List<CommentResponseDto> comments = commentRepository.findByBoardTypeAndPostId(boardType, postId).stream()
        .map(CommentConverter::toResponseDto)
        .sorted(Comparator.comparing(CommentResponseDto::getCreatedAt))
        .collect(Collectors.toList());
    return toCommentsForest(comments);
  }

  private List<CommentResponseDto> toCommentsForest(List<CommentResponseDto> comments) {
    Map<Long, CommentResponseDto> commentsMappedById = new HashMap<>();
    for (var comment: comments) {
      commentsMappedById.put(comment.getId(), comment);
    }

    for (var comment: comments) {
      @Nullable CommentResponseDto parent = commentsMappedById.get(comment.getParentId());
      if (Objects.isNull(parent)) {
        continue;
      }
      parent.addChild(comment);
    }
    return comments.stream()
        .filter(c -> Objects.isNull(c.getParentId()))
        .collect(Collectors.toList());
  }

  public void createComment(CommentRequestDto request) {
    assertThatParentDoesNotExistOrParentIsActive(request);
    commentRepository.save(CommentConverter.toEntity(request));
  }

  @Transactional
  public void updateComment(Long id, CommentRequestDto request) {
    Comment commentToUpdate = findCommentById(id);
    assertThatParentDoesNotExistOrParentIsActive(request);
    commentToUpdate.update(CommentConverter.toEntity(request));
    commentRepository.save(commentToUpdate);
  }

  private Comment findCommentById(Long id) {
    return commentRepository.findById(id)
        .orElseThrow(() -> new CommentNotFoundException(id));
  }

  private void assertThatParentDoesNotExistOrParentIsActive(CommentRequestDto request) {
    if (request.getParentId() == null) {
      return;
    }
    Comment parentComment = findParentCommentById(request.getParentId());
    if (Objects.nonNull(parentComment) && !parentComment.isActive()) {
      throw new IllegalArgumentException("삭제된 댓글에 대댓글을 달 수 없습니다.");
    }
  }

  private Comment findParentCommentById(Long id) {
    return commentRepository.findById(id).orElse(null);
  }

  public void deleteComment(Long id) {
    Comment comment = findCommentById(id);
    comment.deactivate();
    commentRepository.save(comment);
  }
}
