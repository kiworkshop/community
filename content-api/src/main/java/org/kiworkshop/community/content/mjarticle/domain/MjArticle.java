package org.kiworkshop.community.content.mjarticle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.common.model.BaseEntity;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class MjArticle extends BaseEntity {
  private String title;
  @Column(columnDefinition = "text")
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
