package org.kiworkshop.community.article.domain.exception;

import javax.persistence.EntityNotFoundException;

public class ArticleNotFoundException extends EntityNotFoundException {
    public ArticleNotFoundException(Long id) {
        super("Article of id:" + id + " is not found.");
    }
}
