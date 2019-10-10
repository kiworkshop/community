package community.content.jgraphy.service;

import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.api.dto.JgraphyPostResponseDto;
import community.content.jgraphy.domain.JgraphyPost;
import community.content.jgraphy.domain.JgraphyPostRepository;
import community.content.jgraphy.exception.JgraphyPostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static community.content.jgraphy.service.JgraphyPostService.JgraphyPostAssembler.toResponseDto;

@RequiredArgsConstructor
@Service
public class JgraphyPostService {
  private final JgraphyPostRepository jgraphyPostRepository;

  public Long createPost(JgraphyPostRequestDto jgraphyPostRequestDto) {
    return jgraphyPostRepository.save(JgraphyPostAssembler.toEntity(jgraphyPostRequestDto)).getId();
  }

  public JgraphyPostResponseDto readPost(Long id) {
    JgraphyPost jgraphyPost = jgraphyPostRepository.findById(id).orElseThrow(() -> new JgraphyPostNotFoundException(id));
    return toResponseDto(jgraphyPost);
  }

  protected static class JgraphyPostAssembler {
    static JgraphyPost toEntity(JgraphyPostRequestDto jgraphyPostRequestDto) {
      return JgraphyPost.builder()
          .title(jgraphyPostRequestDto.getTitle())
          .content(jgraphyPostRequestDto.getContent())
          .build();
    }

    static JgraphyPostResponseDto toResponseDto(JgraphyPost jgrpahyPost) {
      return JgraphyPostResponseDto.of(jgrpahyPost);
    }
  }

}
