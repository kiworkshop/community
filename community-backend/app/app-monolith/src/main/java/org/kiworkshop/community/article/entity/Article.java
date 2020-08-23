package org.kiworkshop.community.article.entity;

import lombok.Builder;
import lombok.Getter;
import org.kiworkshop.community.common.domain.BaseEntity;

import javax.persistence.Entity;

@Entity
@Getter
public class Article extends BaseEntity {
    private String title;
    private String content;
    private Long userId;

    @Builder
    public Article(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public void update(Article article) {
        this.title = article.title;
        this.content = article.content;
    }
}
