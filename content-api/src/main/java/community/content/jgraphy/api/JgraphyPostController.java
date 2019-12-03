package community.content.jgraphy.api;

import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.api.dto.JgraphyPostResponseDto;
import community.content.jgraphy.service.JgraphyPostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/jgraphy/posts")
public class JgraphyPostController {

  private final JgraphyPostService jgraphyPostService;

  @GetMapping
  public Page<JgraphyPostResponseDto> readPage(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return jgraphyPostService.readPostPage(pageable);
  }

  @PostMapping
  public Long createPost(@RequestBody @Valid JgraphyPostRequestDto jgraphyPostRequestDto) {
    return jgraphyPostService.createPost(jgraphyPostRequestDto);
  }

  @GetMapping("/{id}")
  public JgraphyPostResponseDto readPost(@PathVariable Long id) {
    return jgraphyPostService.readPost(id);
  }

  @DeleteMapping("/{id}")
  public void deletePost(@PathVariable Long id) {
    jgraphyPostService.deletePost(id);
  }

  @PutMapping("/{id}")
  public void updatePost(@PathVariable Long id, @RequestBody JgraphyPostRequestDto jgraphyPostRequestDto) {
    jgraphyPostService.updatePost(id, jgraphyPostRequestDto);
  }
}
