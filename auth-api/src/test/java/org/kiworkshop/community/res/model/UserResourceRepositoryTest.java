package org.kiworkshop.community.res.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// @DataJpaTest
@SpringBootTest
class UserResourceRepositoryTest {

  private @Autowired UserResourceRepository userResourceRepository;

//  @Test
//  void findByUsername_ValidInput_ValidOutput() {
//    // given
//    UserResource userResource = getUserResourceFixture();
//    ReflectionTestUtils.setField(userResource, "id", null);
//    userResourceRepository.save(userResource);
//
//    then(userResourceRepository.findByUsername(userResource.getUsername())).isNotEmpty();
//  }
}
