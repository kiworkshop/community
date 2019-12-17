package community.content.simplelife.article.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleArticleRepository extends JpaRepository<SimpleArticle, Long> {
}
