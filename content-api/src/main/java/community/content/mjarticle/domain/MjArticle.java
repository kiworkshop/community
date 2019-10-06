package community.content.mjarticle.domain;

import community.common.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class MjArticle extends BaseEntity {
  @Column(columnDefinition = "text")
  private String title;
  private String content;

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
