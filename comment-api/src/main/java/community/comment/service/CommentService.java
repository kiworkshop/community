package community.comment.service;

import community.comment.api.dto.CommentRequestDto;
import community.comment.api.dto.CommentResponseDto;
import community.comment.domain.Comment;
import community.comment.domain.CommentRepository;
import community.comment.exception.CommentNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
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
    commentRepository.save(Comment.from(request));
  }

  public void deleteComment(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CommentNotFoundException(id));
    comment.deactivate();
    commentRepository.save(comment);
  }
}
