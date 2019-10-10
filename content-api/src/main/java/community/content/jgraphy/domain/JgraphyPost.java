package community.content.jgraphy.domain;

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
public class JgraphyPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

}
