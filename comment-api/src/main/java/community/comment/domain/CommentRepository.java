package community.comment.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByBoardIdAndPostId(Long boardId, Long postId);
  Optional<Comment> findById(Long id);
}
