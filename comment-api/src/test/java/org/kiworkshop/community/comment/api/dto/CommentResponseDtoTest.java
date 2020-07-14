package org.kiworkshop.community.comment.api.dto;

import java.time.ZonedDateTime;
import org.springframework.test.util.ReflectionTestUtils;

public class CommentResponseDtoTest {
  public static CommentResponseDto getCommentResponseDtoFixture(Long id) throws Exception {
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