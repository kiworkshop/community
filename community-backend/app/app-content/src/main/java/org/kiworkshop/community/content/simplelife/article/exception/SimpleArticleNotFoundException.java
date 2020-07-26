package org.kiworkshop.community.content.simplelife.article.exception;

import javax.persistence.EntityNotFoundException;

public class SimpleArticleNotFoundException extends EntityNotFoundException {
  public SimpleArticleNotFoundException(Long id) {
    super("post id " + id + " has not been found");
  }
}
