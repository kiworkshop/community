package community.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagContentRepository extends JpaRepository<TagContent, Long> {

    List<TagContent> findByContentTypeAndContentId(String contentType, Long contentId);

    List<TagContent> findByTag(Tag tag);
}
