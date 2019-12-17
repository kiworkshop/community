package community.content.myanglog.service;

import community.content.myanglog.api.dto.MyangPostRequestDto;
import community.content.myanglog.api.dto.MyangPostResponseDto;
import community.content.myanglog.domain.MyangPost;
import community.content.myanglog.domain.MyangPostRepository;
import community.content.myanglog.domain.MyangTag;
import community.content.myanglog.domain.MyangTagRepository;
import community.content.myanglog.exception.MyangPostNotFoundException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyangPostService {
  private final MyangPostRepository myangPostRepository;
  private final MyangTagRepository myangTagRepository;
  private final ModelMapper modelMapper;

  @Transactional
  public MyangPostResponseDto readPost(Long id) {
    MyangPost myangPostToRead = findPostById(id);
    myangPostToRead.incrementViewCount();
    return MyangPostResponseDto.from(findPostById(id));
  }

  private MyangPost findPostById(Long id) {
    return myangPostRepository.findById(id).orElseThrow(() -> new MyangPostNotFoundException(id));
  }

  public Long createPost(MyangPostRequestDto myangPostRequestDto) {
    MyangPost myangPost = MyangPost.builder()
        .title(myangPostRequestDto.getTitle())
        .content(myangPostRequestDto.getContent())
        .build();
    myangPost.setMyangTags(myangPostRequestDto.getMyangTags().stream()
        .map(this::findTagByName)
        .collect(Collectors.toSet()));
    return myangPostRepository.save(myangPost).getId();
  }

  private MyangTag findTagByName(MyangTag tag) {
    return myangTagRepository.findByName(tag.getName()).orElse(new MyangTag(tag.getName()));
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
