package community.content.myanglog.api;

import community.content.myanglog.api.dto.MyangPostRequestDto;
import community.content.myanglog.api.dto.MyangPostResponseDto;
import community.content.myanglog.service.MyangPostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/myanglog/posts")
public class MyangPostController {
  private final MyangPostService myangPostService;

  @GetMapping(value = "/{id}")
  public MyangPostResponseDto read(@PathVariable Long id) {
    return myangPostService.readPost(id);
  }

  @PostMapping
  public Long create(@RequestBody @Valid MyangPostRequestDto myangPostRequestDto) {
    return myangPostService.createPost(myangPostRequestDto);
  }

  @PutMapping(value = "/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid MyangPostRequestDto myangPostRequestDto) {
    myangPostService.updatePost(id, myangPostRequestDto);
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    myangPostService.deletePost(id);
  }
}
