package community.content.myanglog.exception;

import javax.persistence.EntityNotFoundException;

public class MyangPostNotFoundException extends EntityNotFoundException {
  public MyangPostNotFoundException(Long id) {
    super("post id -" + id + " is not found.");
  }
}
