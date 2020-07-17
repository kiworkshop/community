package org.kiworkshop.community.comment.domain;

import java.util.List;
import org.kiworkshop.community.comment.dtos.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByBoardTypeAndPostId(BoardType boardType, Long postId);
}
