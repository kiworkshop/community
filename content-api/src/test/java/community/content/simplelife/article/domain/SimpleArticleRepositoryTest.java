package community.content.simplelife.article.domain;

import static community.content.simplelife.article.domain.SimpleTagTest.getTagsFixture;
import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class SimpleArticleRepositoryTest {
  @Autowired
  private SimpleArticleRepository simpleArticleRepository;
  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void save_ValidInput_ValidOutput() {
    // given
    Set<SimpleTag> simpleTags = getTagsFixture();
    SimpleArticle simpleArticle = SimpleArticle.builder()
        .title("title")
        .description("description")
        .content("content")
        .simpleTags(simpleTags).build();

    // when
    SimpleArticle savedSimpleArticle = simpleArticleRepository.save(simpleArticle);

    // then
    SimpleArticle foundSimpleArticle = testEntityManager.find(SimpleArticle.class, savedSimpleArticle.getId());

    then(foundSimpleArticle).hasNoNullFieldsOrPropertiesExcept("updatedAt");
    then(foundSimpleArticle.getTitle()).isEqualTo("title");
    then(foundSimpleArticle.getDescription()).isEqualTo("description");
    then(foundSimpleArticle.getContent()).isEqualTo("content");
    then(foundSimpleArticle.getCreatedAt()).isBeforeOrEqualTo(now());
    then(foundSimpleArticle.getSimpleTags()).isEqualTo(simpleTags);
  }

  @Test
  void find_ValidInput_ValidOutput() {
    // given
    Set<SimpleTag> simpleTags = getTagsFixture();
    SimpleArticle simpleArticle = SimpleArticle.builder()
        .title("title")
        .description("description")
        .content("content")
        .simpleTags(simpleTags).build();

    SimpleArticle savedSimpleArticle = testEntityManager.persist(simpleArticle);

    // when
    SimpleArticle foundSimpleArticle = simpleArticleRepository.findById(savedSimpleArticle.getId()).orElseThrow();

    // then
    then(foundSimpleArticle).hasNoNullFieldsOrPropertiesExcept("updatedAt");
    then(foundSimpleArticle.getTitle()).isEqualTo("title");
    then(foundSimpleArticle.getDescription()).isEqualTo("description");
    then(foundSimpleArticle.getContent()).isEqualTo("content");
    then(foundSimpleArticle.getCreatedAt()).isBeforeOrEqualTo(now());
    then(foundSimpleArticle.getSimpleTags()).isEqualTo(simpleTags);
  }
}