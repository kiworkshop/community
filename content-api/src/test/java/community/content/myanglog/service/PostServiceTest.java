package community.content.myanglog.service;

import static community.content.myanglog.api.dto.PostRequestDtoTest.getPostRequestDtoFixture;
import static community.content.myanglog.domain.PostTest.getPostFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import community.content.myanglog.api.dto.PostRequestDto;
import community.content.myanglog.api.dto.PostResponseDto;
import community.content.myanglog.domain.Post;
import community.content.myanglog.domain.PostRepository;
import community.content.myanglog.exception.PostNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
  private PostService postService;

  private @Mock PostRepository postRepository;

  @BeforeEach
  void setUp() {
    postService = new PostService(postRepository);
  }

  @Test
  void readPost_ValidInput_ValidOutput() throws Exception {
    // given
    Post post = getPostFixture();
    given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
    // when
    PostResponseDto postResponseDto = postService.readPost(1L);
    // expect
    then(postResponseDto)
        .hasFieldOrPropertyWithValue("id", post.getId())
        .hasFieldOrPropertyWithValue("title", post.getTitle())
        .hasFieldOrPropertyWithValue("content", post.getContent())
        .hasFieldOrPropertyWithValue("likeCount", post.getLikeCount())
        .hasFieldOrPropertyWithValue("viewCount", post.getViewCount());
  }

  @Test
  void readPost_NonExistentIdInput_ThrowPostNotFoundException() {
    given(postRepository.findById(anyLong())).willReturn(Optional.empty());
    thenThrownBy(() -> postService.readPost(1L)).isInstanceOf(PostNotFoundException.class);
  }

  @Test
  void createPost_ValidInput_CreatedPostId() throws Exception {
    PostRequestDto request = getPostRequestDtoFixture();
    Post postToSave = getPostFixture();
    given(postRepository.save(any(Post.class))).willReturn(postToSave);

    Long id = postService.createPost(request);

    then(id).isEqualTo(postToSave.getId());
  }

  @Test
  void updatePost_ValidInput_postUpdated() throws Exception {
    PostRequestDto request = getPostRequestDtoFixture();
    Post post = getPostFixture();
    given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));
    given(postRepository.save(any(Post.class))).willReturn(post);

    postService.updatePost(1L, request);
  }

  @Test
  void deletePost_ValidInput_postDeleted() throws Exception {
    Post post = getPostFixture();
    given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

    postService.deletePost(post.getId());
  }
}
