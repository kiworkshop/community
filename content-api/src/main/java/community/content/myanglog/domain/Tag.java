package community.content.myanglog.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String tagName;

  @ManyToMany(mappedBy = "tags")
  private Set<Post> post;

  @Builder
  private Tag(String tagName) {
    Assert.hasLength(tagName, "tag name should not be empty.");
    this.tagName = tagName;
  }
}
