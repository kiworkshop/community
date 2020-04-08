package community.tag.domain;

import community.common.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class TagContent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
    private String contentType;
    private Long contentId;

    @Builder
    private TagContent(
        Tag tag,
        String contentType,
        Long contentId
    ) {
        Assert.notNull(tag, "tag should not be null.");
        Assert.hasLength(contentType, "contentType should not be empty.");
        Assert.notNull(contentId, "contentId should not be null.");

        this.tag = tag;
        this.contentType = contentType;
        this.contentId = contentId;
    }
}
