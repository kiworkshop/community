package org.kiworkshop.community.content.mjarticle.exception;

import javax.persistence.EntityNotFoundException;

public class MjArticleNotFoundException extends EntityNotFoundException {
  public MjArticleNotFoundException(Long id) {
    super("mjArticle id " + id + " has not been found");
  }
}
