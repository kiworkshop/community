package community.content.myanglog.api.dto;

import community.content.myanglog.domain.MyangTag;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MyangPostRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
  private @NotNull Set<MyangTag> myangTags = new HashSet<>();
}
