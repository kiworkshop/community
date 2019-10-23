package community.content.simplelife.tag.domain;

import community.content.simplelife.post.domain.Post;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
  private String name;
  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "tags")
  private Set<Post> posts = new HashSet<>();

  @Builder
  private Tag(String name
  ) {
    Assert.hasLength(name, "tagName should not be empty.");

    this.name = name;
  }
}
