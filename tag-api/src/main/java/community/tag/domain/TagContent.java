package community.tag.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class TagContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
    private String contentType;
    private Long contentId;

    @Builder
    private TagContent(Tag tag, String contentType, Long contentId) {
        this.tag = tag;
        this.contentType = contentType;
        this.contentId = contentId;
    }
}
