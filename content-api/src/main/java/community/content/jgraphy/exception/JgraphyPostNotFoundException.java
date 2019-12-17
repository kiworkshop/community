package community.content.jgraphy.exception;

import javax.persistence.EntityNotFoundException;

public class JgraphyPostNotFoundException extends EntityNotFoundException {
  public JgraphyPostNotFoundException(Long id) {
    super("JgraphyPost id " + id + " has not been found");
  }
}
