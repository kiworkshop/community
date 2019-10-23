package community.content.simplelife.post.domain;

import community.content.simplelife.tag.domain.Tag;
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
public class Post {
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
  private int viewCount;
  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "post_tags",
          joinColumns = { @JoinColumn(name = "post_id") },
          inverseJoinColumns = { @JoinColumn(name = "tag_id") })
  private Set<Tag> tags = new HashSet<>();

  @Builder
  private Post(
      String title,
      String description,
      String content
  ) {
    Assert.hasLength(title, "title should not be empty.");
    Assert.hasLength(description, "description should not be empty.");
    Assert.hasLength(content, "content should not be empty.");

    this.title = title;
    this.description = description;
    this.content = content;
  }
}
