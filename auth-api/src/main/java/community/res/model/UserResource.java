package community.res.model;

import community.auth.model.Social;
import community.common.model.BaseEntity;
import community.common.utils.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor
class UserResource extends BaseEntity {

  @Column(nullable = false, unique = true, length = 36)
  private @UUID String username; // TODO: map OneToOne with User.
  private Social social;
  @Column(unique = true)
  private String nickname;
  private @Email String contactEmail;

  @Builder
  private UserResource(
      @NonNull String username,
      @NonNull Social social,
      @NonNull String nickname,
      @Email String contactEmail
  ) {
    this.username = username;
    this.social = social;
    this.nickname = nickname;
    this.contactEmail = contactEmail;
  }
}
