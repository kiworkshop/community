package org.kiworkshop.community.res.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResourceRepository extends JpaRepository<UserResource, Long> {
//  Optional<UserResource> findByUsername(String username);
}
