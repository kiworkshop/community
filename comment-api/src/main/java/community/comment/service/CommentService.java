package community.comment.service;

import community.comment.api.dto.CommentRequestDto;
import community.comment.api.dto.CommentResponseDto;
import community.comment.domain.Comment;
import community.comment.domain.CommentRepository;
import community.comment.exception.CommentNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;

  public List<CommentResponseDto> getComments(Long boardId, Long postId) {
    return commentRepository.findByBoardIdAndPostId(boardId, postId).stream()
        .map(CommentResponseDto::of)
        .collect(Collectors.toList());
  }

  public void createComment(CommentRequestDto request) {
    validate(request);
    commentRepository.save(Comment.from(request));
  }

  @Transactional
  public void updateComment(Long id, CommentRequestDto request) {
    Comment commentToUpdate = findCommentById(id);
    validate(request);
    commentToUpdate.update(request);
    commentRepository.save(commentToUpdate);
  }

  private Comment findCommentById(Long id) {
    return commentRepository.findById(id)
        .orElseThrow(() -> new CommentNotFoundException(id));
  }

  private void validate(CommentRequestDto request) {
    if (request.getParentId() != null) {
      Comment parentComment = findCommentById(request.getParentId());
      if (!parentComment.isActive()) {
        throw new IllegalArgumentException("삭제된 댓글에 대댓글을 달 수 없습니다.");
      }
    }
  }

  public void deleteComment(Long id) {
    Comment comment = findCommentById(id);
    comment.deactivate();
    commentRepository.save(comment);
  }
}
