package community.content.myanglog.service;

import community.content.myanglog.api.dto.PostResponseDto;
import community.content.myanglog.domain.Post;
import community.content.myanglog.domain.PostRepository;
import community.content.myanglog.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;

  public PostResponseDto readPost(Long id) {
    return PostResponseDto.of(findPostById(id));
  }

  private Post findPostById(Long id) {
    return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
  }
}
