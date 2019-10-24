package community.content.simplelife.article.api;

import community.content.simplelife.article.api.dto.SimpleArticleRequestDto;
import community.content.simplelife.article.api.dto.SimpleArticleResponseDto;
import community.content.simplelife.article.service.SimpleArticleService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/simplelife/posts")
public class SimpleArticleController {
  private final SimpleArticleService simpleArticleService;

  @PostMapping
  public Long create(@RequestBody @Valid SimpleArticleRequestDto simpleArticleRequestDto) {
    return simpleArticleService.createPost(simpleArticleRequestDto);
  }

  @GetMapping("/{id}")
  public SimpleArticleResponseDto read(@PathVariable Long id) {
    return simpleArticleService.readPost(id);
  }

  @GetMapping
  public Page<SimpleArticleResponseDto> readPage(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return simpleArticleService.readPostPage(pageable);
  }

  @PostMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid SimpleArticleRequestDto simpleArticleRequestDto) {
    simpleArticleService.updatePost(id, simpleArticleRequestDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    simpleArticleService.deletePost(id);
  }
}
