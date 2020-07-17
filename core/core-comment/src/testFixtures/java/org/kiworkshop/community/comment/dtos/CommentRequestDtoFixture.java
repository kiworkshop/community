package org.kiworkshop.community.comment.dtos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentRequestDtoFixture {
  public static CommentRequestDto get() {
    CommentRequestDto commentRequestDto = new CommentRequestDto();

    ReflectionTestUtils.setField(commentRequestDto, "boardType", BoardType.NOTICE);
    ReflectionTestUtils.setField(commentRequestDto, "postId", 1L);
    ReflectionTestUtils.setField(commentRequestDto, "parentId", 1L);
    ReflectionTestUtils.setField(commentRequestDto, "username", "user1");
    ReflectionTestUtils.setField(commentRequestDto, "content", "content");

    return commentRequestDto;
  }
}