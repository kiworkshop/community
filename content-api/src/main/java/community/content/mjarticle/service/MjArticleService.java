package community.content.mjarticle.service;

import community.content.mjarticle.api.dto.MjArticleDetailResponseDto;
import community.content.mjarticle.api.dto.MjArticleRequestDto;
import community.content.mjarticle.api.dto.MjArticleResponseDto;
import community.content.mjarticle.domain.MjArticle;
import community.content.mjarticle.domain.MjArticleRepository;
import community.content.mjarticle.exception.MjArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MjArticleService {
  private final MjArticleRepository repository;
  private final ModelMapper modelMapper;

  public Long createArticle(MjArticleRequestDto mjArticleRequestDto) {
    return repository.save(createEntityFrom(mjArticleRequestDto)).getId();
  }

  public Page<MjArticleResponseDto> readArticlePage(Pageable pageable) {
    return repository.findAll(pageable).map(this::createResponseDtoFrom);
  }

  public MjArticleResponseDto readArticle(Long id) {
    MjArticle curr = findById(id);
    MjArticle prev = repository.findFirstByIdBeforeOrderByIdDesc(id).orElse(null);
    MjArticle next = repository.findFirstByIdAfterOrderByIdAsc(id).orElse(null);

    return MjArticleDetailResponseDto.builder().curr(curr).prev(prev).next(next).build();
  }

  public void updateArticle(Long id, MjArticleRequestDto mjArticleRequestDto) {
    MjArticle mjArticleToUpdate = findById(id);
    mjArticleToUpdate.updateMjArticle(createEntityFrom(mjArticleRequestDto));
    repository.save(mjArticleToUpdate);
  }

  public void deleteById(Long id) {
    MjArticle mjArticleToDelete = findById(id);
    repository.delete(mjArticleToDelete);
  }

  private MjArticle findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new MjArticleNotFoundException(id));
  }

  private MjArticle createEntityFrom(MjArticleRequestDto request) {
    return modelMapper.map(request, MjArticle.class);
  }

  private MjArticleResponseDto createResponseDtoFrom(MjArticle mjArticle) {
    return modelMapper.map(mjArticle, MjArticleResponseDto.class);
  }
}
