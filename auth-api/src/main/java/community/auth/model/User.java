package community.auth.model;

import community.common.model.BaseEntity;
import community.common.utils.UUID;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails, CredentialsContainer {

  @Column(nullable = false, unique = true, length = 36)
  private @UUID String username;
  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private List<Authority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;

  @Builder
  private User(
      @NonNull String username,
      @NonNull String password
  ) {
    this.username = username;
    this.password = password;
    this.authorities = Collections.singletonList(Authority.USER);
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;
  }

  public void eraseCredentials() {
    password = null;
  }
}
