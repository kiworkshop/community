package org.kiworkshop.community.mother.comment.domain.service;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.comment.dtos.BoardType;
import org.kiworkshop.community.comment.dtos.CommentRequestDto;
import org.kiworkshop.community.comment.dtos.CommentRequestDtoFixture;
import org.kiworkshop.community.comment.dtos.CommentResponseDto;
import org.kiworkshop.community.mother.comment.domain.exception.CommentNotFoundException;
import org.kiworkshop.community.mother.comment.domain.model.Comment;
import org.kiworkshop.community.mother.comment.domain.model.CommentFixture;
import org.kiworkshop.community.mother.comment.domain.model.CommentRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
  private CommentService commentService;
  private @Mock CommentRepository commentRepository;

  @BeforeEach
  void setUp() {
    commentService = new CommentService(commentRepository);
  }

  @Test
  void getComments_ValidInput_ValidForest() {
    List<Comment> comments =  LongStream.rangeClosed(1, 10).mapToObj(i -> {
      Comment comment = new Comment();
      ReflectionTestUtils.setField(comment, "id", i);
      ReflectionTestUtils.setField(comment, "boardType", BoardType.NOTICE);
      ReflectionTestUtils.setField(comment, "postId", 1L);
      ReflectionTestUtils.setField(comment, "content", "content " + i);
      ReflectionTestUtils.setField(comment, "createdAt", ZonedDateTime.now().minusYears(1).plusDays(i));
      return comment;
    }).collect(Collectors.toList());

    // 1 <- 2
    ReflectionTestUtils.setField(comments.get(1), "parentId", 1L);
    // 2 <- 3
    ReflectionTestUtils.setField(comments.get(2), "parentId", 2L);
    // 1 <- 4
    ReflectionTestUtils.setField(comments.get(3), "parentId", 1L);
    // 5
    // 6
    // 7 <- 8
    ReflectionTestUtils.setField(comments.get(7), "parentId", 7L);
    // 7 <- 9
    ReflectionTestUtils.setField(comments.get(8), "parentId", 7L);
    // 9 <- 10
    ReflectionTestUtils.setField(comments.get(9), "parentId", 9L);
    //      1        5       6        7
    //    2   4                     8   9
    //    3                             10

    comments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));

    given(commentRepository.findByBoardTypeAndPostId(eq(BoardType.NOTICE), anyLong())).willReturn(comments);

    // when
    List<CommentResponseDto> forest = commentService.getComments(BoardType.NOTICE, 1L);

    // then stop and verify manually... how can i automatically verify this?
    return;
  }

  @Test
  void createComment_ValidInput_ValidOutput() {
    CommentRequestDto request = CommentRequestDtoFixture.get();
    Comment commentToSave = CommentFixture.get();
    given(commentRepository.save(any(Comment.class))).willReturn(commentToSave);

    commentService.createComment(request);
  }

  @Test
  void createComment_CommentToDeletedParentComment_throwsException() {
    CommentRequestDto request = CommentRequestDtoFixture.get();
    Comment parentComment = CommentFixture.getDeactivated();
    given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(parentComment));

    thenThrownBy(() -> commentService.createComment(request))
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void updateComment_ValidInput_ValidOutput() {
    CommentRequestDto commentRequestDto = CommentRequestDtoFixture.get();
    Comment comment = CommentFixture.getDeactivated();
    given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
    given(commentRepository.save(any(Comment.class))).willReturn(comment);

    commentService.updateComment(1L, commentRequestDto);
  }

  @Test
  void deleteComment_ValidInput_ValidOutput() {
    Comment commentToDelete = CommentFixture.getDeactivated();
    given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(commentToDelete));

    commentService.deleteComment(commentToDelete.getId());
  }

  @Test
  void deleteComment_InvalidInput_throwsException() {
    given(commentRepository.findById(any(Long.class))).willReturn(Optional.empty());
    thenThrownBy(() -> commentService.deleteComment(1L))
        .isExactlyInstanceOf(CommentNotFoundException.class);
  }
}