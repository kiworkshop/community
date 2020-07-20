package org.kiworkshop.community.user.resource.domain.model;

import static java.time.ZonedDateTime.now;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResourceFixture {
  public static UserResource get() {
    UserResource userResource = UserResource.builder()
        .userId(1L)
        .username("username")
        .nickname("nickname")
        .contactEmail("foo@bar.com").build();

    ReflectionTestUtils.setField(userResource, "id", 1L);
    ReflectionTestUtils.setField(userResource, "createdAt", now());
    ReflectionTestUtils.setField(userResource, "updatedAt", now());

    return userResource;
  }
}
