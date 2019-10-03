package community.content.mjarticle.domain;

import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class MjArticleRepositoryTest {
  @Autowired
  private MjArticleRepository mjArticleRepository;
  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void save_ValidInput_ValidOutput() {
    // given
    MjArticle mjArticle = MjArticle.builder()
        .title("title")
        .content("content").build();

    // when
    MjArticle savedMjArticle = mjArticleRepository.save(mjArticle);

    // then
    MjArticle foundMjArticle = testEntityManager.find(MjArticle.class, savedMjArticle.getId());

    then(foundMjArticle).hasNoNullFieldsOrProperties();
    then(foundMjArticle.getTitle()).isEqualTo("title");
    then(foundMjArticle.getContent()).isEqualTo("content");
    then(foundMjArticle.getCreatedAt()).isBeforeOrEqualTo(now());
  }

  @Test
  void find_ValidInput_ValidOutput() {
    // given
    MjArticle mjArticle = MjArticle.builder()
        .title("title")
        .content("content").build();

    MjArticle savedMjArticle = testEntityManager.persist(mjArticle);

    // when
    Optional<MjArticle> foundMjArticle = mjArticleRepository.findById(savedMjArticle.getId());

    // then
    MjArticle found = foundMjArticle.orElseThrow();

    then(found).hasNoNullFieldsOrProperties();
    then(found.getTitle()).isEqualTo("title");
    then(found.getContent()).isEqualTo("content");
    then(found.getCreatedAt()).isBeforeOrEqualTo(now());
  }
}
