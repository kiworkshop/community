package community.content.myanglog.api.dto;

import community.content.myanglog.domain.MyangTag;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MyangPostRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
  private Set<MyangTag> myangTags;
}
