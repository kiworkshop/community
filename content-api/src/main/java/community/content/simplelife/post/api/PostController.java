package community.content.simplelife.post.api;

import community.content.simplelife.post.api.dto.PostRequestDto;
import community.content.simplelife.post.api.dto.PostResponseDto;
import community.content.simplelife.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/simplelife/posts")
public class PostController {
  private final PostService postService;

  @PostMapping
  public Long create(@RequestBody @Valid PostRequestDto postRequestDto) {
    return postService.createPost(postRequestDto);
  }

  @GetMapping("/{id}")
  public PostResponseDto read(@PathVariable Long id) {
    return postService.readPost(id);
  }

  @GetMapping
  public Page<PostResponseDto> readPage(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return postService.readPostPage(pageable);
  }

  @PostMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid PostRequestDto postRequestDto) {
    postService.updatePost(id, postRequestDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    postService.deletePost(id);
  }
}
