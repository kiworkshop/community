package community.content.jgraphy.service;

import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.domain.JgraphyPost;
import community.content.jgraphy.domain.JgraphyPostRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static community.content.jgraphy.api.dto.JgraphyPostRequestDtoTest.getJgraphyPostRequestDtoFixture;
import static community.content.jgraphy.domain.JgraphyPostTest.getJgraphyPostFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JgraphyPostServiceTest {
  private JgraphyPostService jgraphyPostService;

  private @Mock JgraphyPostRepository jgraphyPostRepository;

  @BeforeEach
  void setUp() { jgraphyPostService = new JgraphyPostService(jgraphyPostRepository); }

  @Test
  void createJgraphyPost_validInput_validOutput() throws Exception {
    // given
    JgraphyPostRequestDto jgraphyPostRequestDto = getJgraphyPostRequestDtoFixture();

    JgraphyPost jgraphyPostToSave = getJgraphyPostFixture();
    given(jgraphyPostRepository.save(any(JgraphyPost.class))).willReturn(jgraphyPostToSave);

    // when
    Long id = jgraphyPostService.createPost(jgraphyPostRequestDto);

    then(id).isEqualTo(jgraphyPostToSave.getId());
  }
}