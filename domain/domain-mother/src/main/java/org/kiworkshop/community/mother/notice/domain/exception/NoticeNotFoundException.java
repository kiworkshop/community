package org.kiworkshop.community.mother.notice.domain.exception;

import javax.persistence.EntityNotFoundException;

public class NoticeNotFoundException extends EntityNotFoundException {
  public NoticeNotFoundException(Long id) {
    super("notice id " + id + " has not been found");
  }
}
