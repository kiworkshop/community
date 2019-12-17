package community.content.simplelife.article.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleTagRepository extends JpaRepository<SimpleTag, Long> {
  Optional<SimpleTag> findByName(String name);
}
