package community.content.jgraphy.service;

import static community.content.jgraphy.api.dto.JgraphyPostRequestDtoTest.getJgraphyPostRequestDtoFixture;
import static community.content.jgraphy.domain.JgraphyPostTest.getJgraphyPostFixture;
import static community.content.jgraphy.service.JgraphyPostService.JgraphyPostAssembler.toEntity;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.api.dto.JgraphyPostResponseDto;
import community.content.jgraphy.domain.JgraphyPost;
import community.content.jgraphy.domain.JgraphyPostRepository;
import community.content.jgraphy.exception.JgraphyPostNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JgraphyPostServiceTest {
  private JgraphyPostService jgraphyPostService;

  private @Mock JgraphyPostRepository jgraphyPostRepository;

  @BeforeEach
  void setUp() {
    jgraphyPostService = new JgraphyPostService(jgraphyPostRepository);
  }

  @Test
  void readJgraphyPostPage_validInput_validOutput() {
    //given
    final Long numJgraphyPost = 10L;
    List<JgraphyPost> jgraphyPosts = new ArrayList<>();

    for (long i = 1; i < 11L; i++) {
      jgraphyPosts.add(getJgraphyPostFixture(i));
    }

    PageImpl<JgraphyPost> jgraphyPostPage = new PageImpl<>(jgraphyPosts);
    given(jgraphyPostRepository.findAll(any(Pageable.class))).willReturn(jgraphyPostPage);

    //when
    Page<JgraphyPostResponseDto> jgraphyPostResponseDtoPage = jgraphyPostService.readPostPage(
        PageRequest.of(0, numJgraphyPost.intValue()));

    //expect
    then(jgraphyPostResponseDtoPage.getTotalElements()).isEqualTo(numJgraphyPost);
  }

  @Test
  void createJgraphyPost_validInput_validOutput() {
    // given
    JgraphyPostRequestDto jgraphyPostRequestDto = getJgraphyPostRequestDtoFixture();

    JgraphyPost jgraphyPostToSave = getJgraphyPostFixture();
    given(jgraphyPostRepository.save(any(JgraphyPost.class))).willReturn(jgraphyPostToSave);

    // when
    Long id = jgraphyPostService.createPost(jgraphyPostRequestDto);

    then(id).isEqualTo(jgraphyPostToSave.getId());
  }

  @Test
  void createJgraphyPost_emptyFields_throwsException() {
    // given
    JgraphyPostRequestDto jgraphyPostRequestDto = new JgraphyPostRequestDto();

    thenThrownBy(() -> toEntity(jgraphyPostRequestDto)).isExactlyInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void readJgraphyPost_validInput_validOutput() throws Exception {
    // given
    JgraphyPost jgraphyPost = getJgraphyPostFixture();
    given(jgraphyPostRepository.findById(1L)).willReturn(Optional.of(jgraphyPost));

    JgraphyPostResponseDto jgraphyPostResponseDto = jgraphyPostService.readPost(1L);

    then(jgraphyPostResponseDto)
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("id", jgraphyPost.getId())
        .hasFieldOrPropertyWithValue("title", jgraphyPost.getTitle())
        .hasFieldOrPropertyWithValue("content", jgraphyPost.getContent());
  }

  @Test
  void readJgraphyPost_NotExistingId_JgraphyPostNotFoundException() {
    given(jgraphyPostRepository.findById(1L)).willReturn(Optional.empty());
    thenThrownBy(() -> jgraphyPostService.readPost(1L)).isExactlyInstanceOf(JgraphyPostNotFoundException.class);
  }

  @Test
  void deleteJgraphyPost_validInput_validOutput() {
    JgraphyPost jgraphyPostToDelete = getJgraphyPostFixture();
    given(jgraphyPostRepository.findById(any(Long.class))).willReturn(Optional.of(jgraphyPostToDelete));

    jgraphyPostService.deletePost(jgraphyPostToDelete.getId());
  }

  @Test
  void updateJgraphyPost_validInput_validOutput() {
    JgraphyPostRequestDto jgraphyPostRequestDto = getJgraphyPostRequestDtoFixture();
    JgraphyPost jgraphyPost = getJgraphyPostFixture();
    given(jgraphyPostRepository.findById(anyLong())).willReturn(Optional.of(jgraphyPost));

    jgraphyPostService.updatePost(jgraphyPost.getId(), jgraphyPostRequestDto);

    JgraphyPostResponseDto jgraphyPostResponseDto = jgraphyPostService.readPost(jgraphyPost.getId());
    then(jgraphyPostResponseDto)
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("id", jgraphyPost.getId())
        .hasFieldOrPropertyWithValue("title", jgraphyPostRequestDto.getTitle())
        .hasFieldOrPropertyWithValue("content", jgraphyPostRequestDto.getContent());

  }

  @Test
  void findJgrpahyPost_validInput_validOutput() {
    JgraphyPost jgraphyPostToFind = getJgraphyPostFixture();
    given(jgraphyPostRepository.findById(any(Long.class))).willReturn(Optional.of(jgraphyPostToFind));

    JgraphyPost jgraphyPost = ReflectionTestUtils.invokeMethod(
        jgraphyPostService, "findJgraphyPostById", jgraphyPostToFind.getId());

    then(jgraphyPost)
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("id", jgraphyPostToFind.getId())
        .hasFieldOrPropertyWithValue("title", jgraphyPostToFind.getTitle())
        .hasFieldOrPropertyWithValue("content", jgraphyPostToFind.getContent());

  }

  @Test
  void findJgraphyPost_invalidInput_Exception() {
    given(jgraphyPostRepository.findById(any(Long.class))).willReturn(Optional.empty());

    thenThrownBy(() -> ReflectionTestUtils.invokeMethod(jgraphyPostService, "findJgraphyPostById", 1L))
        .isExactlyInstanceOf(JgraphyPostNotFoundException.class);

  }
}