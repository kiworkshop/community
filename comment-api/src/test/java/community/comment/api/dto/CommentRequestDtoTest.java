package community.comment.api.dto;

import static org.junit.jupiter.api.Assertions.*;

import community.common.model.BoardType;
import org.springframework.test.util.ReflectionTestUtils;

public class CommentRequestDtoTest {
  public static CommentRequestDto getCommentRequestDtoFixture() {
    CommentRequestDto commentRequestDto = new CommentRequestDto();

    ReflectionTestUtils.setField(commentRequestDto, "boardType", BoardType.NOTICE);
    ReflectionTestUtils.setField(commentRequestDto, "postId", 1L);
    ReflectionTestUtils.setField(commentRequestDto, "parentId", 1L);
    ReflectionTestUtils.setField(commentRequestDto, "username", "user1");
    ReflectionTestUtils.setField(commentRequestDto, "content", "content");

    return commentRequestDto;
  }
}