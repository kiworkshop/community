package community.content.simplelife.article.domain;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class SimpleArticle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private String content;
  @CreationTimestamp
  private ZonedDateTime createdAt;
  @UpdateTimestamp
  private ZonedDateTime updatedAt;
  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "post_tag",
          joinColumns = { @JoinColumn(name = "post_id") },
          inverseJoinColumns = { @JoinColumn(name = "tag_id") })
  private Set<SimpleTag> simpleTags = new HashSet<>();

  @Builder
  private SimpleArticle(
      String title,
      String description,
      String content,
      Set<SimpleTag> simpleTags
  ) {
    Assert.hasLength(title, "title should not be empty.");
    Assert.hasLength(description, "description should not be empty.");
    Assert.hasLength(content, "content should not be empty.");

    this.title = title;
    this.description = description;
    this.content = content;
    this.simpleTags = simpleTags;
  }

  public void addTags(Set<SimpleTag> simpleTags) {
    simpleTags.forEach(tag -> tag.addArticle(this));
    this.simpleTags.addAll(simpleTags);
  }

  public void updateTag(Set<SimpleTag> simpleTags) {
    this.simpleTags = simpleTags;
  }

  public void update(SimpleArticle simpleArticle, Set<SimpleTag> simpleTags) {
    this.simpleTags.forEach(simpleTag -> simpleTag.deletePost(this));

    this.title = simpleArticle.title;
    this.description = simpleArticle.description;
    this.content = simpleArticle.content;
    this.simpleTags = simpleTags;
  }
}
