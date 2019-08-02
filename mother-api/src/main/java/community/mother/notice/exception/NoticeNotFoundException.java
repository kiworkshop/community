package community.mother.notice.exception;

public class NoticeNotFoundException extends RuntimeException {
  public NoticeNotFoundException(Long id) {
    super("notice id " + id + " has not been found");
  }
}
