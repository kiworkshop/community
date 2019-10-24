package community.content.simplelife.article.service;

import community.content.simplelife.article.api.dto.SimpleArticleRequestDto;
import community.content.simplelife.article.api.dto.SimpleArticleResponseDto;
import community.content.simplelife.article.domain.SimpleArticle;
import community.content.simplelife.article.domain.SimpleArticleRepository;
import community.content.simplelife.article.domain.SimpleTag;
import community.content.simplelife.article.exception.SimpleArticleNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleArticleService {
  private final SimpleArticleRepository simpleArticleRepository;

  public Long createPost(SimpleArticleRequestDto simpleArticleRequestDto) {
    Set<SimpleTag> simpleTags = simpleArticleRequestDto.getSimpleTags().stream()
        .map(tag -> SimpleTag.builder().name(tag).build()).collect(Collectors.toSet());
    SimpleArticle simpleArticle = SimpleArticle.builder().title(simpleArticleRequestDto.getTitle())
        .description(simpleArticleRequestDto.getDescription())
        .content(simpleArticleRequestDto.getContent())
        .simpleTags(simpleTags).build();

    return simpleArticleRepository.save(simpleArticle).getId();
  }

  public SimpleArticleResponseDto readPost(Long id) {
    return SimpleArticleResponseDto.of(findPostById(id));
  }

  private SimpleArticle findPostById(Long id) {
    return simpleArticleRepository.findById(id).orElseThrow(() -> new SimpleArticleNotFoundException(id));
  }

  public void updatePost(Long id, SimpleArticleRequestDto simpleArticleRequestDto) {
    SimpleArticle simpleArticle = findPostById(id);
    simpleArticle.update(simpleArticleRequestDto.getTitle(),
        simpleArticleRequestDto.getDescription(),
        simpleArticleRequestDto.getContent(),
        simpleArticleRequestDto.getSimpleTags().stream()
            .map(tag -> SimpleTag.builder().name(tag).build()).collect(Collectors.toSet()));
  }

  public void deletePost(Long id) {
    simpleArticleRepository.delete(findPostById(id));
  }

  public Page<SimpleArticleResponseDto> readPostPage(Pageable pageable) {
    return simpleArticleRepository.findAll(pageable).map(SimpleArticleResponseDto::of);
  }
}
