package community.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsTagByName(String name);

    Optional<Tag> findByName(String name);
}
