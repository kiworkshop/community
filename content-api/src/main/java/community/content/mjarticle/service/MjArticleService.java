package community.content.mjarticle.service;

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
  private final MjArticleRepository mjArticleRepository;
  private final ModelMapper modelMapper;

  public Long createMjArticle(MjArticleRequestDto mjArticleRequestDto) {
    return mjArticleRepository.save(createEntityFrom(mjArticleRequestDto)).getId();
  }

  public Page<MjArticleResponseDto> readMjArticlePage(Pageable pageable) {
    return mjArticleRepository.findAll(pageable).map(this::createResponseDtoFrom);
  }

  public MjArticleResponseDto readMjArticle(Long id) {
    return createResponseDtoFrom(findById(id));
  }

  public void updateMjArticle(Long id, MjArticleRequestDto mjArticleRequestDto) {
    MjArticle mjArticleToUpdate = findById(id);
    mjArticleToUpdate.updateMjArticle(createEntityFrom(mjArticleRequestDto));
    mjArticleRepository.save(mjArticleToUpdate);
  }

  public void deleteById(Long id) {
    MjArticle mjArticleToDelete = findById(id);
    mjArticleRepository.delete(mjArticleToDelete);
  }

  private MjArticle findById(Long id) {
    return mjArticleRepository.findById(id).orElseThrow(() -> new MjArticleNotFoundException(id));
  }

  private MjArticle createEntityFrom(MjArticleRequestDto request) {
    return modelMapper.map(request, MjArticle.class);
  }

  private MjArticleResponseDto createResponseDtoFrom(MjArticle mjArticle) {
    return modelMapper.map(mjArticle, MjArticleResponseDto.class);
  }
}
