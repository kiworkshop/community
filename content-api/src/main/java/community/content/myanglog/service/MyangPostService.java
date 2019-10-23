package community.content.myanglog.service;

import community.content.myanglog.api.dto.MyangPostRequestDto;
import community.content.myanglog.api.dto.MyangPostResponseDto;
import community.content.myanglog.domain.MyangPost;
import community.content.myanglog.domain.MyangPostRepository;
import community.content.myanglog.domain.TagRepository;
import community.content.myanglog.exception.MyangPostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyangPostService {
  private final MyangPostRepository myangPostRepository;
  private final TagRepository tagRepository;
  private final ModelMapper modelMapper;

  public MyangPostResponseDto readPost(Long id) {
    MyangPost myangPostToRead = findPostById(id);
    myangPostToRead.incrementViewCount();
    return MyangPostResponseDto.of(findPostById(id));
  }

  private MyangPost findPostById(Long id) {
    return myangPostRepository.findById(id).orElseThrow(() -> new MyangPostNotFoundException(id));
  }

  public Long createPost(MyangPostRequestDto myangPostRequestDto) {
    MyangPost myangPost = MyangPost.builder()
        .title(myangPostRequestDto.getTitle())
        .content(myangPostRequestDto.getContent())
        .build();
    myangPost.setTags(myangPostRequestDto.getTags());
    return myangPostRepository.save(myangPost).getId();
  }

  public void updatePost(Long id, MyangPostRequestDto myangPostRequestDto) {
    MyangPost myangPostToUpdate = findPostById(id);
    myangPostToUpdate.updatePost(createEntityFrom(myangPostRequestDto));
    myangPostRepository.save(myangPostToUpdate);
  }

  public void deletePost(Long id) {
    MyangPost myangPostToDelete = findPostById(id);
    myangPostRepository.delete(myangPostToDelete);
  }

  private MyangPost createEntityFrom(MyangPostRequestDto myangPostRequestDto) {
    return modelMapper.map(myangPostRequestDto, MyangPost.class);
  }
}
