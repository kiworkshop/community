package community.content.board_myeongjae.exception;

import javax.persistence.EntityNotFoundException;

public class MjArticleNotFoundException extends EntityNotFoundException {
  public MjArticleNotFoundException(Long id) {
    super("mjArticle id " + id + " has not been found");
  }
}
