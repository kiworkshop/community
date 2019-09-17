package community.content.board_myeongjae.service;

import community.content.board_myeongjae.api.dto.MjArticleRequestDto;
import community.content.board_myeongjae.api.dto.MjArticleResponseDto;
import community.content.board_myeongjae.domain.MjArticle;
import community.content.board_myeongjae.domain.MjArticleRepository;
import community.content.board_myeongjae.exception.MjArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MjArticleService {
  private final MjArticleRepository mjArticleRepository;

  public Long createMjArticle(MjArticleRequestDto mjArticleRequestDto) {
    return mjArticleRepository.save(MjArticle.builder()
        .title(mjArticleRequestDto.getTitle())
        .content(mjArticleRequestDto.getContent()).build())
        .getId();
  }

  public Page<MjArticleResponseDto> readMjArticlePage(Pageable pageable) {
    return mjArticleRepository.findAll(pageable).map(MjArticleResponseDto::of);
  }

  public MjArticleResponseDto readMjArticle(Long id) {
    return MjArticleResponseDto.of(findMjArticleById(id));
  }

  public void updateMjArticle(Long id, MjArticleRequestDto mjArticleRequestDto) {
    MjArticle mjArticleToUpdate = findMjArticleById(id);
    mjArticleToUpdate.updateMjArticle(mjArticleRequestDto.getTitle(), mjArticleRequestDto.getContent());
    mjArticleRepository.save(mjArticleToUpdate);
  }

  public void deleteById(Long id) {
    MjArticle mjArticleToDelete = findMjArticleById(id);
    mjArticleRepository.delete(mjArticleToDelete);
  }

  private MjArticle findMjArticleById(Long id) {
    return mjArticleRepository.findById(id).orElseThrow(() -> new MjArticleNotFoundException(id));
  }
}
