package org.kiworkshop.community.article.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.kiworkshop.community.common.domain.BaseEntity;
import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Article extends BaseEntity {
    private static final String DELETED_ARTICLE_MESSAGE = "삭제된 게시글입니다.";

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String username;
    private boolean active = true;

    @Builder
    private Article(String title, String content, String username) {
        Assert.hasLength(title, "title must have length.");
        Assert.hasLength(content, "content must have length.");
        Assert.hasLength(username, "username must have length.");
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public void update(Article article) {
        // TODO: 20. 8. 31. Error Code 가 400이 되어야 할지? 403이 되어야 할지 고민
        Assert.isTrue(this.username.equals(article.username), "unauthorized username");
        this.title = article.title;
        this.content = article.content;
    }

    public boolean isAuthor(String name) {
        return this.username.equals(name);
    }

    public void deactivate(String username) {
        if (!this.username.equals(username)) {
            throw new IllegalArgumentException("unauthorized user");
        }
        this.active = false;
    }

    public String getTitle() {
        if (this.active) {
            return DELETED_ARTICLE_MESSAGE;
        }
        return this.title;
    }

    public String getContent() {
        if (this.active) {
            return DELETED_ARTICLE_MESSAGE;
        }
        return this.content;
    }
}
