package community.content.myanglog.api.dto;

import community.content.myanglog.domain.Tag;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class PostRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
  private Set<Tag> tags;
}
