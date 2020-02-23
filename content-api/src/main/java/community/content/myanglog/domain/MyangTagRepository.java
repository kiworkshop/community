package community.content.myanglog.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyangTagRepository extends JpaRepository<MyangTag, Long> {
  Optional<MyangTag> findByName(String name);
}
