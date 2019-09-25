package community.content.simplelife.post.api.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Getter
public class PostRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String description;
  private @NotEmpty String content;
  private List<String> imageUrls;
  private Set<String> tags;
}
