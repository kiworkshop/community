package org.kiworkshop.community.user.resource.domain.exception;

public class UserResourceNotFoundException extends RuntimeException {
  public static UserResourceNotFoundException fromUsername(String username) {
    return new UserResourceNotFoundException("An user resource has not been found. username: " + username);
  }

  public UserResourceNotFoundException(String msg) {
    super(msg);
  }
}
