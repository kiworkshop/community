package community.res.model;

import community.common.model.BaseEntity;
import community.common.utils.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
class UserResource extends BaseEntity {

  @Column(nullable = false, unique = true, length = 36)
  private @UUID String username;
  private @Embedded Social social;
  @Column(unique = true)
  private String nickname;
  private @Email String contactEmail;

  @Builder
  private UserResource(
      String username,
      Social social,
      String nickname,
      String contactEmail
  ) {
    this.username = username;
    this.social = social;
    this.nickname = nickname;
    this.contactEmail = contactEmail;
  }
}
