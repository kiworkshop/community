package community.content.jgraphy.api;

import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.api.dto.JgraphyPostResponseDto;
import community.content.jgraphy.service.JgraphyPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/jgraphy/posts")
public class JgraphyPostController {

  private final JgraphyPostService jgraphyPostService;

  @PostMapping
  public void createPost(@RequestBody JgraphyPostRequestDto jgraphyPostRequestDto) {
    jgraphyPostService.createPost(jgraphyPostRequestDto);
  }

  @GetMapping("/{id}")
  public JgraphyPostResponseDto readPost(@PathVariable Long id) {
    return jgraphyPostService.readPost(id);
  }

}
