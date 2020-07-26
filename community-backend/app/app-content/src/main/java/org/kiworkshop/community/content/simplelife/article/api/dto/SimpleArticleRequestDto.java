package org.kiworkshop.community.content.simplelife.article.api.dto;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleArticle;

@Getter
public class SimpleArticleRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String description;
  private @NotEmpty String content;
  private Set<String> simpleTags;

  public SimpleArticle createSimpleArticle() {
    return SimpleArticle.builder()
        .title(title)
        .description(description)
        .content(content)
        .build();
  }
}
