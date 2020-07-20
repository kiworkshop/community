package org.kiworkshop.community.user.resource.domain.model;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
class UserResourceRepositoryTest {

  private @Autowired UserResourceRepository userResourceRepository;

  @Test
  void findByUsername_ValidInput_ValidOutput() {
    // given
    UserResource userResource = UserResourceFixture.get();
    ReflectionTestUtils.setField(userResource, "id", null);
    userResourceRepository.save(userResource);

    then(userResourceRepository.findByUsername(userResource.getUsername())).isNotEmpty();
  }

  @Test
  void findByUserId_ValidInput_ValidOutput() {
    // given
    UserResource userResource = UserResourceFixture.get();
    ReflectionTestUtils.setField(userResource, "id", null);
    userResourceRepository.save(userResource);

    then(userResourceRepository.findByUserId(userResource.getUserId())).isNotEmpty();
  }
}
