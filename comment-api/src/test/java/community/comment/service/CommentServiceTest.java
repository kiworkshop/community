package community.comment.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import community.comment.api.dto.CommentResponseDto;
import community.comment.domain.Comment;
import community.comment.domain.CommentRepository;
import community.common.model.BoardType;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
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
  void getComments_ValidInput_ValidForest() throws JsonProcessingException {
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

    given(commentRepository.findByBoardIdAndPostId(anyLong(), anyLong())).willReturn(comments);

    // when
    List<CommentResponseDto> forest = commentService.getComments(1L, 1L);

    // then
    ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
    System.out.println(mapper.writeValueAsString(forest));
    return;
  }

}