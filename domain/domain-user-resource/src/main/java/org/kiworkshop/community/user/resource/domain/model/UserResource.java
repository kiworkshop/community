package org.kiworkshop.community.user.resource.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.kiworkshop.community.common.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class UserResource extends BaseEntity {

  @Column(nullable = false, unique = true)
  private Long userId; // same value with User#id
  @Column(nullable = false, unique = true)
  private String username; // same value with User#username
  @Column(nullable = false, unique = true)
  private String nickname;
  private String contactEmail;

  @Builder
  private UserResource(
      @NonNull Long userId,
      @NonNull String username,
      @NonNull String nickname,
      @NonNull String contactEmail
  ) {
    this.userId = userId;
    this.username = username;
    this.nickname = nickname;
    this.contactEmail = contactEmail;
  }
}
