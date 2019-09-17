package community.content.myanglog.exception;

import javax.persistence.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
  public PostNotFoundException(Long id) {
    super("post id -" + id + " is not found.");
  }
}
