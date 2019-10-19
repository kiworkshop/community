package community.content.myanglog.service;

import community.content.myanglog.api.dto.MyangPostRequestDto;
import community.content.myanglog.api.dto.MyangPostResponseDto;
import community.content.myanglog.domain.MyangPost;
import community.content.myanglog.domain.MyangPostRepository;
import community.content.myanglog.exception.MyangPostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyangPostService {
  private final MyangPostRepository myangPostRepository;

  public MyangPostResponseDto readPost(Long id) {
    MyangPost myangPostToRead = findPostById(id);
    myangPostToRead.incrementViewCount();
    return MyangPostResponseDto.of(findPostById(id));
  }

  private MyangPost findPostById(Long id) {
    return myangPostRepository.findById(id).orElseThrow(() -> new MyangPostNotFoundException(id));
  }

  public Long createPost(MyangPostRequestDto myangPostRequestDto) {
    return myangPostRepository.save(MyangPost.builder()
        .title(myangPostRequestDto.getTitle())
        .content(myangPostRequestDto.getContent())
        .tags(myangPostRequestDto.getTags())
        .build()).getId();
  }

  public void updatePost(Long id, MyangPostRequestDto myangPostRequestDto) {
    MyangPost myangPostToUpdate = findPostById(id);
    myangPostToUpdate.updatePost(myangPostRequestDto.getTitle(), myangPostRequestDto.getContent());
    myangPostRepository.save(myangPostToUpdate);
  }

  public void deletePost(Long id) {
    MyangPost myangPostToDelete = findPostById(id);
    myangPostRepository.delete(myangPostToDelete);
  }
}
