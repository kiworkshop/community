package community.mother.notice.domain;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class NoticeRepositoryTest {
  private @Autowired TestEntityManager testEntityManager;
  private @Autowired NoticeRepository noticeRepository;

  @Test
  void findById_savedId_FoundSavedEntity() throws Exception {
    // given
    Notice notice = testEntityManager.persist(Notice.builder()
        .title("title")
        .content("content").build());
    then(notice.getId()).isNotNull();

    // when
    Notice foundNotice = noticeRepository.findById(notice.getId())
        .orElseThrow(() -> new Exception("notice not found"));

    // then
    then(notice.getId()).isEqualTo(foundNotice.getId());
  }

  @Test
  void findById_deletedId_FoundOptionalEmpty() throws Exception {
    // given
    Notice notice = testEntityManager.persist(Notice.builder()
        .title("title")
        .content("content").build());
    then(notice.getId()).isNotNull();

    // when
    Notice noticeToDelete = noticeRepository.findById(notice.getId())
        .orElseThrow(() -> new Exception("notice not found"));
    noticeRepository.delete(noticeToDelete);

    // then
    then(noticeRepository.findById(noticeToDelete.getId())).isEmpty();
  }

}
