package community.auth.exception;

public class UserNotFoundException extends RuntimeException {
  public static UserNotFoundException fromSocialId(String socialId) {
    return new UserNotFoundException("An user has not been found. socialId: " + socialId);
  }

  public static UserNotFoundException fromUsername(String username) {
    return new UserNotFoundException("An user has not been found. username: " + username);
  }

  public UserNotFoundException(String msg) {
    super(msg);
  }
}
