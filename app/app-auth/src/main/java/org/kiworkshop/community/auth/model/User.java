package org.kiworkshop.community.auth.model;

import static java.util.UUID.randomUUID;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.common.domain.BaseEntity;
import org.kiworkshop.community.validation.Uuid;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

  @Column(nullable = false, unique = true, length = 36)
  private @Uuid String username;
  private @Embedded Social social;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private Set<Authority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;

  @Builder
  private User(Social social) {
    this.username = randomUUID().toString();
    this.social = social;
    this.authorities = Collections.singleton(Authority.USER);
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;
  }

  public String getSocialId() {
    return this.social.getSocialId();
  }
}
