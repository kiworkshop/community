package community.content.simplelife.article.service;

import community.content.simplelife.article.api.dto.SimpleArticleRequestDto;
import community.content.simplelife.article.api.dto.SimpleArticleResponseDto;
import community.content.simplelife.article.domain.SimpleArticle;
import community.content.simplelife.article.domain.SimpleArticleRepository;
import community.content.simplelife.article.domain.SimpleTag;
import community.content.simplelife.article.domain.SimpleTagRepository;
import community.content.simplelife.article.exception.SimpleArticleNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SimpleArticleService {
  private final SimpleArticleRepository simpleArticleRepository;
  private final SimpleTagRepository simpleTagRepository;

  public Long createArticle(SimpleArticleRequestDto simpleArticleRequestDto) {
    SimpleArticle simpleArticle = simpleArticleRequestDto.createSimpleArticle();
    simpleArticle.updateTag(createSimpleTag(simpleArticleRequestDto.getSimpleTags()));
    return simpleArticleRepository.save(simpleArticle).getId();
  }

  private Set<SimpleTag> createSimpleTag(Set<String> simpleTags) {
    return simpleTags.stream().map(this::findTagByName).collect(Collectors.toSet());
  }

  public SimpleArticleResponseDto readPost(Long id) {
    return SimpleArticleResponseDto.of(findArticleById(id));
  }

  @Transactional
  public void updateArticle(Long id, SimpleArticleRequestDto simpleArticleRequestDto) {
    SimpleArticle simpleArticle = findArticleById(id);
    Set<SimpleTag> simpleTags = createSimpleTag(simpleArticleRequestDto.getSimpleTags());

    simpleArticle.update(simpleArticleRequestDto.createSimpleArticle(), simpleTags);
  }

  public void deleteArticle(Long id) {
    simpleArticleRepository.delete(findArticleById(id));
  }

  public Page<SimpleArticleResponseDto> readArticlePage(Pageable pageable) {
    return simpleArticleRepository.findAll(pageable).map(SimpleArticleResponseDto::of);
  }

  private SimpleArticle findArticleById(Long id) {
    return simpleArticleRepository.findById(id).orElseThrow(() -> new SimpleArticleNotFoundException(id));
  }

  private SimpleTag findTagByName(String name) {
    return simpleTagRepository.findByName(name).orElse(SimpleTag.builder().name(name).build());
  }
}
