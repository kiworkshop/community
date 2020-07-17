package org.kiworkshop.community.comment.domain;

import org.kiworkshop.community.comment.dtos.BoardType;
import org.springframework.test.util.ReflectionTestUtils;

public class CommentTest {
  public static Comment getCommentFixture() {
    Comment comment = new Comment();

    ReflectionTestUtils.setField(comment, "id", 1L);
    ReflectionTestUtils.setField(comment, "boardType", BoardType.NOTICE);
    ReflectionTestUtils.setField(comment, "postId", 1L);
    ReflectionTestUtils.setField(comment, "username", "user1");
    ReflectionTestUtils.setField(comment, "content", "content");

    return comment;
  }

  public static Comment getDeactivatedParentCommentFixture() {
    Comment comment = new Comment();

    ReflectionTestUtils.setField(comment, "boardType", BoardType.NOTICE);
    ReflectionTestUtils.setField(comment, "postId", 1L);
    ReflectionTestUtils.setField(comment, "username", "user1");
    ReflectionTestUtils.setField(comment, "content", "content");
    ReflectionTestUtils.setField(comment, "active", false);

    return comment;
  }
}