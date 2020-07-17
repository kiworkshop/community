package org.kiworkshop.community.mother.comment.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.comment.dtos.BoardType;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentFixture {
  public static Comment get() {
    Comment comment = new Comment();

    ReflectionTestUtils.setField(comment, "id", 1L);
    ReflectionTestUtils.setField(comment, "boardType", BoardType.NOTICE);
    ReflectionTestUtils.setField(comment, "postId", 1L);
    ReflectionTestUtils.setField(comment, "username", "user1");
    ReflectionTestUtils.setField(comment, "content", "content");

    return comment;
  }

  public static Comment getDeactivated() {
    Comment comment = new Comment();

    ReflectionTestUtils.setField(comment, "boardType", BoardType.NOTICE);
    ReflectionTestUtils.setField(comment, "postId", 1L);
    ReflectionTestUtils.setField(comment, "username", "user1");
    ReflectionTestUtils.setField(comment, "content", "content");
    ReflectionTestUtils.setField(comment, "active", false);

    return comment;
  }
}