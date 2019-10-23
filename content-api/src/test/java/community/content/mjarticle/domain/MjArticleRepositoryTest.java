package community.content.mjarticle.domain;

import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
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

  @Test
  void findFirstByIdBeforeOrderByIdDesc_ValidInput_ValidOutput() throws Exception {
    // given
    LongStream.range(0, 10).forEach((i) -> testEntityManager.persist(MjArticle.builder()
        .title("title")
        .content("content").build()));

    List<MjArticle> result = mjArticleRepository.findAll();

    Long id = result.get(result.size() / 2).getId();
    Long prevId = result.get(result.size() / 2 - 1).getId();

    // expect
    mjArticleRepository.findFirstByIdBeforeOrderByIdDesc(id)
        .map(foundArticle -> then(foundArticle.getId()).isEqualTo(prevId))
        .orElseThrow(() -> new Exception("test has been failed."));
  }

  @Test
  void findFirstByIdAfterOrderByIdAsc_ValidInput_ValidOutput() throws Exception {
    // given
    LongStream.range(0, 10).forEach((i) -> testEntityManager.persist(MjArticle.builder()
        .title("title")
        .content("content").build()));

    List<MjArticle> result = mjArticleRepository.findAll();

    Long id = result.get(result.size() / 2).getId();
    Long nextId = result.get(result.size() / 2 + 1).getId();

    // expect
    mjArticleRepository.findFirstByIdAfterOrderByIdAsc(id)
        .map(foundArticle -> then(foundArticle.getId()).isEqualTo(nextId))
        .orElseThrow(() -> new Exception("test has been failed."));
  }
}
