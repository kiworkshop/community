package org.kiworkshop.community.content.mjarticle.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MjArticleRepository extends JpaRepository<MjArticle, Long> {

  public Optional<MjArticle> findFirstByIdBeforeOrderByIdDesc(Long id);

  public Optional<MjArticle> findFirstByIdAfterOrderByIdAsc(Long id);
}
