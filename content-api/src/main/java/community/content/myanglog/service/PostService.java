package community.content.myanglog.service;

import community.content.myanglog.api.dto.PostRequestDto;
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
    Post postToRead = findPostById(id);
    postToRead.incrementViewCount();
    return PostResponseDto.of(findPostById(id));
  }

  private Post findPostById(Long id) {
    return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
  }

  public Long createPost(PostRequestDto postRequestDto) {
    return postRepository.save(Post.builder()
        .title(postRequestDto.getTitle())
        .content(postRequestDto.getContent())
        .tags(postRequestDto.getTags())
        .build()).getId();
  }

  public void updatePost(Long id, PostRequestDto postRequestDto) {
    Post postToUpdate = findPostById(id);
    postToUpdate.updatePost(postRequestDto.getTitle(), postRequestDto.getContent());
    postRepository.save(postToUpdate);
  }

  public void deletePost(Long id) {
    Post postToDelete = findPostById(id);
    postRepository.delete(postToDelete);
  }
}
