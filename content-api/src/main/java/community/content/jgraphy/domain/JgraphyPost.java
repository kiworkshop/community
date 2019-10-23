package community.content.jgraphy.domain;

import community.common.model.BaseEntity;
import jdk.jfr.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class JgraphyPost extends BaseEntity {

  private String title;
  private String content;

  @Builder
  private JgraphyPost(
      String title,
      String content
  ) {
    Assert.hasLength(title, "title should be needed");
    Assert.hasLength(content, "content should be needed");
    this.title = title;
    this.content = content;
  }

  public void update(final JgraphyPost jgraphyPost) {
    this.title = jgraphyPost.getTitle();
    this.content = jgraphyPost.getContent();
  }

}
