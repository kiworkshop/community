package community.comment.api;

import community.comment.api.dto.CommentRequestDto;
import community.comment.api.dto.CommentResponseDto;
import community.comment.service.CommentService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;

  @GetMapping(value = "/{boardId}/{postId}")
  public List<CommentResponseDto> get(@PathVariable Long boardId, @PathVariable Long postId) {
    return commentService.getComments(boardId, postId);
  }

  @PostMapping(value = {"", "/"})
  public void post(@Valid @RequestBody CommentRequestDto request) {
    commentService.createComment(request);
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    commentService.deleteComment(id);
  }
}
