package community.auth.model;

import community.common.utils.UUID;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class User implements UserDetails, CredentialsContainer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String password;

  private @UUID String username;

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
