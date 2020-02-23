package community.auth.model;

import community.common.model.BaseEntity;
import community.common.utils.UUID;
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

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

  @Column(nullable = false, unique = true, length = 36)
  private @UUID String username;
  private @Embedded Social social;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private Set<Authority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;

  @Builder
  private User(String username, Social social) {
    this.username = username;
    this.social = social;
    this.authorities = Collections.singleton(Authority.USER);
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;
  }

  public void eraseCredentials() {
    this.social = Social.empty;
  }

  public String getSocialId() {
    return this.social.getSocialId();
  }
}
