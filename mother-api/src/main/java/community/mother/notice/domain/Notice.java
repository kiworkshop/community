package community.mother.notice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class Notice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;

  @Builder
  private Notice(
      String content,
      String title
  ) {
    Assert.hasLength(title, "title should not be empty.");
    Assert.hasLength(content, "content should not be empty.");

    this.title = title;
    this.content = content;
  }
}
