package community.content.myanglog.api;

import community.content.myanglog.api.dto.PostResponseDto;
import community.content.myanglog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/myanglog/posts")
public class PostController {
  private final PostService postService;

  @GetMapping(value = "/{id}")
  public PostResponseDto read(@PathVariable Long id) {
    return postService.readPost(id);
  }
}
