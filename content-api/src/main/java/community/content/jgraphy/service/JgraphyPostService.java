package community.content.jgraphy.service;

import static community.content.jgraphy.service.JgraphyPostService.JgraphyPostAssembler.toEntity;
import static community.content.jgraphy.service.JgraphyPostService.JgraphyPostAssembler.toResponseDto;

import community.content.jgraphy.api.dto.JgraphyPostRequestDto;
import community.content.jgraphy.api.dto.JgraphyPostResponseDto;
import community.content.jgraphy.domain.JgraphyPost;
import community.content.jgraphy.domain.JgraphyPostRepository;
import community.content.jgraphy.exception.JgraphyPostNotFoundException;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JgraphyPostService {
  private final JgraphyPostRepository jgraphyPostRepository;

  public Page<JgraphyPostResponseDto> readPostPage(Pageable pageable) {
    return jgraphyPostRepository.findAll(pageable).map(JgraphyPostAssembler::toResponseDto);
  }

  public Long createPost(JgraphyPostRequestDto jgraphyPostRequestDto) {
    return jgraphyPostRepository.save(toEntity(jgraphyPostRequestDto)).getId();
  }

  public JgraphyPostResponseDto readPost(Long id) {
    JgraphyPost jgraphyPost = findJgraphyPostById(id);
    return toResponseDto(jgraphyPost);
  }

  public void deletePost(Long id) {
    JgraphyPost jgraphyPost = findJgraphyPostById(id);
    jgraphyPostRepository.delete(jgraphyPost);
  }

  @Transactional
  public void updatePost(Long id, JgraphyPostRequestDto jgraphyPostRequestDto) {
    JgraphyPost jgraphyPost = findJgraphyPostById(id);
    JgraphyPost updateValue = toEntity(jgraphyPostRequestDto);
    jgraphyPost.update(updateValue);
  }

  private JgraphyPost findJgraphyPostById(Long id) {
    return jgraphyPostRepository.findById(id).orElseThrow(() -> new JgraphyPostNotFoundException(id));
  }

  protected static class JgraphyPostAssembler {
    static JgraphyPost toEntity(JgraphyPostRequestDto jgraphyPostRequestDto) {
      return JgraphyPost.builder()
          .title(jgraphyPostRequestDto.getTitle())
          .content(jgraphyPostRequestDto.getContent())
          .build();
    }

    static JgraphyPostResponseDto toResponseDto(JgraphyPost jgrpahyPost) {
      return JgraphyPostResponseDto.builder()
          .id(jgrpahyPost.getId())
          .title(jgrpahyPost.getTitle())
          .content(jgrpahyPost.getContent())
          .createdAt(jgrpahyPost.getCreatedAt())
          .updatedAt(jgrpahyPost.getUpdatedAt())
          .build();
    }
  }

}
