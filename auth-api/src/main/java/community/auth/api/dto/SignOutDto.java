package community.auth.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignOutDto {
  @JsonProperty("refresh_token")
  private @NotEmpty String refreshToken;
}
