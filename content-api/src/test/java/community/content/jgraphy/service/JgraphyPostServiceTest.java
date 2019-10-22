package community.content.jgraphy.service;

import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.api.dto.JgraphyPostResponseDto;
import community.content.jgraphy.domain.JgraphyPost;
import community.content.jgraphy.domain.JgraphyPostRepository;
import community.content.jgraphy.exception.JgraphyPostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static community.content.jgraphy.api.dto.JgraphyPostRequestDtoTest.getJgraphyPostRequestDtoFixture;
import static community.content.jgraphy.domain.JgraphyPostTest.getJgraphyPostFixture;
import static community.content.jgraphy.service.JgraphyPostService.JgraphyPostAssembler.toEntity;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JgraphyPostServiceTest {
  private JgraphyPostService jgraphyPostService;

  private @Mock JgraphyPostRepository jgraphyPostRepository;

  @BeforeEach
  void setUp() { jgraphyPostService = new JgraphyPostService(jgraphyPostRepository); }

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
}