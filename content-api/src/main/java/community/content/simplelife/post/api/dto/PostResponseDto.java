package community.content.simplelife.post.api.dto;

import community.content.simplelife.post.domain.Post;
import community.content.simplelife.tag.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostResponseDto {
  private Long id;
  private String title;
  private String description;
  private String content;
  private List<String> imageUrls;
  private Set<String> tags;

  private PostResponseDto(Post post) {
    this.id = post.getId();
    this.title = post.getTitle();
    this.description = post.getDescription();
    this.content = post.getContent();
    this.imageUrls = post.getImageUrls();
    this.tags = post.getTags().stream().map(Tag::getTagName).collect(Collectors.toSet());
  }

  public static PostResponseDto of(Post post) {
    return new PostResponseDto(post);
  }
}
