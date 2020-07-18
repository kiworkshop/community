package org.kiworkshop.community.user.resource.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResourceRepository extends JpaRepository<UserResource, Long> {
//  Optional<UserResource> findByUsername(String username);
}
