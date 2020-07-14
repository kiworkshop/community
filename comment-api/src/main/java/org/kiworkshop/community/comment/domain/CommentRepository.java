package org.kiworkshop.community.comment.domain;

import community.common.model.BoardType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByBoardTypeAndPostId(BoardType boardType, Long postId);
}
