package community.content.simplelife.article.domain;

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
public class SimpleTag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "simpleTags")
  private Set<SimpleArticle> simpleArticles = new HashSet<>();

  @Builder
  private SimpleTag(String name) {
    Assert.hasLength(name, "tagName should not be empty.");

    this.name = name;
  }

  public void addArticle(SimpleArticle simpleArticle) {
    this.simpleArticles.add(simpleArticle);
  }

  public void deletePost(SimpleArticle simpleArticle) {
    this.simpleArticles.remove(simpleArticle);
  }
}
