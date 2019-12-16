package community.content.myanglog.service;

import static community.content.myanglog.api.dto.MyangPostRequestDtoTest.getMyangPostRequestDtoFixture;
import static community.content.myanglog.domain.MyangPostTest.getMyangPostFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import community.common.config.CommonBeanCreators;
import community.content.myanglog.api.dto.MyangPostRequestDto;
import community.content.myanglog.api.dto.MyangPostResponseDto;
import community.content.myanglog.domain.MyangPost;
import community.content.myanglog.domain.MyangPostRepository;
import community.content.myanglog.domain.MyangTagRepository;
import community.content.myanglog.exception.MyangPostNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyangPostServiceTest {
  private MyangPostService myangPostService;

  private @Mock MyangPostRepository myangPostRepository;
  private @Mock MyangTagRepository myangTagRepository;

  @BeforeEach
  void setUp() {
    myangPostService = new MyangPostService(myangPostRepository, myangTagRepository, CommonBeanCreators.modelMapper());
  }

  @Test
  void readPost_ValidInput_ValidOutput() throws Exception {
    // given
    MyangPost myangPost = getMyangPostFixture();
    given(myangPostRepository.findById(anyLong())).willReturn(Optional.of(myangPost));
    // when
    MyangPostResponseDto myangPostResponseDto = myangPostService.readPost(1L);
    // expect
    then(myangPostResponseDto)
        .hasFieldOrPropertyWithValue("id", myangPost.getId())
        .hasFieldOrPropertyWithValue("title", myangPost.getTitle())
        .hasFieldOrPropertyWithValue("content", myangPost.getContent())
        .hasFieldOrPropertyWithValue("likeCount", myangPost.getLikeCount())
        .hasFieldOrPropertyWithValue("viewCount", myangPost.getViewCount());
  }

  @Test
  void readPost_NonExistentIdInput_ThrowPostNotFoundException() {
    given(myangPostRepository.findById(anyLong())).willReturn(Optional.empty());
    thenThrownBy(() -> myangPostService.readPost(1L)).isInstanceOf(MyangPostNotFoundException.class);
  }

  @Test
  void createPost_ValidInput_CreatedPostId() throws Exception {
    MyangPostRequestDto request = getMyangPostRequestDtoFixture();
    MyangPost myangPostToSave = getMyangPostFixture();
    given(myangPostRepository.save(any(MyangPost.class))).willReturn(myangPostToSave);

    Long id = myangPostService.createPost(request);

    then(id).isEqualTo(myangPostToSave.getId());
  }

  @Test
  void updatePost_ValidInput_postUpdated() throws Exception {
    MyangPostRequestDto request = getMyangPostRequestDtoFixture();
    MyangPost myangPost = getMyangPostFixture();
    given(myangPostRepository.findById(any(Long.class))).willReturn(Optional.of(myangPost));
    given(myangPostRepository.save(any(MyangPost.class))).willReturn(myangPost);

    myangPostService.updatePost(1L, request);
  }

  @Test
  void deletePost_ValidInput_postDeleted() throws Exception {
    MyangPost myangPost = getMyangPostFixture();
    given(myangPostRepository.findById(any(Long.class))).willReturn(Optional.of(myangPost));

    myangPostService.deletePost(myangPost.getId());
  }
}
