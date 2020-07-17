package dtos;

import java.time.ZonedDateTime;
import org.kiworkshop.community.comment.dtos.CommentResponseDto;
import org.springframework.test.util.ReflectionTestUtils;

public class CommentResponseDtoFixture {
  public static CommentResponseDto get(Long id) {
    CommentResponseDto commentResponseDto = new CommentResponseDto();

    ReflectionTestUtils.setField(commentResponseDto, "id", id);
    ReflectionTestUtils.setField(commentResponseDto, "username", "user1");
    ReflectionTestUtils.setField(commentResponseDto, "content", "content");
    ReflectionTestUtils.setField(commentResponseDto, "order", 0);
    ReflectionTestUtils.setField(commentResponseDto, "active", true);
    ReflectionTestUtils.setField(commentResponseDto, "createdAt", ZonedDateTime.now());

    return commentResponseDto;
  }
}