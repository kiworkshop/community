package org.kiworkshop.community.mother.comment.domain.exception;

import javax.persistence.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {
  public CommentNotFoundException(Long id) {
    super("Comment of id:" + id + " is not found.");
  }
}
