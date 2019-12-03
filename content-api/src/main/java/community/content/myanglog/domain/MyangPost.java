package community.content.myanglog.domain;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
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
public class MyangPost {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(
      name = "myang_post_myang_tag",
      joinColumns = @JoinColumn(name = "myang_post_id"),
      inverseJoinColumns = @JoinColumn(name = "myang_tag_id")
  )

  private Set<MyangTag> myangTags = new HashSet<>();

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
      Set<MyangTag> myangTags,
      int likeCount,
      int viewCount
  ) {
    Assert.hasLength(title, "title should not be empty.");
    Assert.hasLength(content, "content should not be empty.");
    this.title = title;
    this.content = content;
    this.myangTags = myangTags;
    this.likeCount = likeCount;
    this.viewCount = viewCount;
    this.createdAt = ZonedDateTime.now();
    this.updatedAt = ZonedDateTime.now();
  }

  public void incrementViewCount() {
    this.viewCount++;
  }

  public void setMyangTags(Set<MyangTag> myangTags) {
    this.myangTags = myangTags;
    myangTags.forEach(tag -> tag.addMyangPosts(this));
  }

  public void updatePost(MyangPost myangPost) {
    this.title = myangPost.title;
    this.content = myangPost.content;
    updateTags(myangPost.getMyangTags());
  }

  private void updateTags(Set<MyangTag> myangTags) {
    removeTags();
    this.myangTags = myangTags;
    myangTags.forEach(tag -> tag.addMyangPosts(this));
  }

  private void removeTags() {
    this.myangTags.forEach(tag -> tag.removeMyangPosts(this));
  }
}
