package org.kiworkshop.community.content.jgraphy.domain;

import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.common.model.BaseEntity;
import org.springframework.util.Assert;

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
    this.title = jgraphyPost.title;
    this.content = jgraphyPost.content;
  }

}
