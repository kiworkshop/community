package community.comment.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByBoardIdAndPostId(Long boardId, Long postId);
}
