package community.content.myanglog.api;

import community.content.myanglog.api.dto.PostRequestDto;
import community.content.myanglog.api.dto.PostResponseDto;
import community.content.myanglog.service.PostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping
  public Long create(@RequestBody @Valid PostRequestDto postRequestDto) {
    return postService.createPost(postRequestDto);
  }

  @PutMapping
  public void update(Long id, @RequestBody @Valid PostRequestDto postRequestDto) {
    postService.updatePost(id, postRequestDto);
  }
}
