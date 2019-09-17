package community.content.myanglog.api.dto;

import community.content.myanglog.domain.Post;
import community.content.myanglog.domain.Tag;
import java.time.ZonedDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
  private Long id;
  private String title;
  private String content;
  private Set<Tag> tags;
  private int likeCount;
  private int viewCount;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  public PostResponseDto(Post post) {
    this.id = post.getId();
    this.title = post.getTitle();
    this.content = post.getContent();
    this.tags = post.getTags();
    this.likeCount = post.getLikeCount();
    this.viewCount = post.getViewCount();
    this.createdAt = post.getCreatedAt();
    this.updatedAt = post.getUpdatedAt();
  }

  public static PostResponseDto of(Post post) {
    return new PostResponseDto(post);
  }
}
