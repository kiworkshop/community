package org.kiworkshop.community.content.simplelife.article.api.dto;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleArticle;
import org.kiworkshop.community.content.simplelife.article.domain.SimpleTag;

@Getter
@NoArgsConstructor
public class SimpleArticleResponseDto {
  private Long id;
  private String title;
  private String description;
  private String content;
  private Set<String> tags;

  private SimpleArticleResponseDto(SimpleArticle simpleArticle) {
    this.id = simpleArticle.getId();
    this.title = simpleArticle.getTitle();
    this.description = simpleArticle.getDescription();
    this.content = simpleArticle.getContent();
    this.tags = simpleArticle.getSimpleTags().stream().map(SimpleTag::getName).collect(Collectors.toSet());
  }

  public static SimpleArticleResponseDto of(SimpleArticle simpleArticle) {
    return new SimpleArticleResponseDto(simpleArticle);
  }
}
