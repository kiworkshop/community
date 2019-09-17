package community.content.myanglog.domain;

import java.time.ZonedDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  @ManyToMany
  @JoinTable(
      name = "POST_TAG",
      joinColumns = @JoinColumn(name = "POST_ID"),
      inverseJoinColumns = @JoinColumn(name = "TAG_ID")
  )
  private Set<Tag> tags;

  private int likeCount;

  private int viewCount;

  @Column(name = "created_at")
  @CreationTimestamp
  private ZonedDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private ZonedDateTime updatedAt;

  @Builder
  private Post(
      String title,
      String content,
      int likeCount,
      int viewCount
  ) {
    Assert.hasLength(title, "title should not be empty.");
    Assert.hasLength(content, "content should not be empty.");
    this.title = title;
    this.content = content;
    this.likeCount = likeCount;
    this.viewCount = viewCount;
    this.createdAt = ZonedDateTime.now();
    this.updatedAt = ZonedDateTime.now();
  }
}
