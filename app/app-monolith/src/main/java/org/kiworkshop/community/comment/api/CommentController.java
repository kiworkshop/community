package org.kiworkshop.community.comment.api;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.comment.dtos.BoardType;
import org.kiworkshop.community.comment.dtos.CommentRequestDto;
import org.kiworkshop.community.comment.dtos.CommentResponseDto;
import org.kiworkshop.community.mother.comment.domain.service.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;

  @GetMapping(value = "/{boardType}/{postId}")
  public List<CommentResponseDto> get(@PathVariable BoardType boardType, @PathVariable Long postId) {
    return commentService.getComments(boardType, postId);
  }

  @PostMapping(value = {"", "/"})
  public void post(@Valid @RequestBody CommentRequestDto request) {
    commentService.createComment(request);
  }

  @PutMapping(value = "/{id}")
  public void put(@PathVariable Long id, @Valid @RequestBody CommentRequestDto request) {
    commentService.updateComment(id, request);
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    commentService.deleteComment(id);
  }
}
