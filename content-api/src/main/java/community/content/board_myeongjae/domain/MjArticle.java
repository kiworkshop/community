package community.content.board_myeongjae.domain;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class MjArticle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;
  @CreationTimestamp
  private ZonedDateTime createdAt;

  @Builder
  private MjArticle(
      String content,
      String title
  ) {
    Assert.hasLength(title, "title should not be empty.");
    Assert.hasLength(content, "content should not be empty.");

    this.title = title;
    this.content = content;
  }

  public void updateMjArticle(MjArticle mjArticle) {
    this.title = mjArticle.title;
    this.content = mjArticle.content;
  }
}
