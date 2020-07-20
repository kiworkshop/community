package org.kiworkshop.community.user.resource.domain.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResourceRepository extends JpaRepository<UserResource, Long> {
  Optional<UserResource> findByUsername(String username);
  Optional<UserResource> findByUserId(Long userId);
}
