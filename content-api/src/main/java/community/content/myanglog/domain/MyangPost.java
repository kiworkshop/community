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
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class MyangPost {
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
  private MyangPost(
      String title,
      String content,
      @Nullable Set<Tag> tags,
      int likeCount,
      int viewCount
  ) {
    Assert.hasLength(title, "title should not be empty.");
    Assert.hasLength(content, "content should not be empty.");
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.likeCount = likeCount;
    this.viewCount = viewCount;
    this.createdAt = ZonedDateTime.now();
    this.updatedAt = ZonedDateTime.now();
  }

  public void incrementViewCount() {
    this.viewCount++;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
    tags.forEach(tag -> tag.getMyangPosts().add(this));
  }

  public void updatePost(MyangPost myangPost) {
    this.title = myangPost.title;
    this.content = myangPost.content;
    updateTags(myangPost.getTags());
  }

  private void updateTags(Set<Tag> tags) {
    removeTags();
    this.tags = tags;
    tags.forEach(tag -> tag.getMyangPosts().add(this));
  }

  private void removeTags() {
    if (this.tags != null) {
      this.tags.forEach(tag -> tag.getMyangPosts().remove(this));
    }
  }
}
