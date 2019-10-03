package community.content.mjarticle.service;

import static community.content.mjarticle.api.dto.MjArticleRequestDtoTest.getMjArticleRequestDtoFixture;
import static community.content.mjarticle.domain.MjArticleTest.getMjArticleFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import community.common.config.CommonBeanCreators;
import community.content.mjarticle.api.dto.MjArticleRequestDto;
import community.content.mjarticle.api.dto.MjArticleResponseDto;
import community.content.mjarticle.domain.MjArticle;
import community.content.mjarticle.domain.MjArticleRepository;
import community.content.mjarticle.exception.MjArticleNotFoundException;
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
class MjArticleServiceTest {
  private MjArticleService mjArticleService;

  private @Mock MjArticleRepository mjArticleRepository;

  @BeforeEach
  void setup() {
    mjArticleService = new MjArticleService(mjArticleRepository, CommonBeanCreators.modelMapper());
  }

  @Test
  void findById_NonExistentMjArticleId_MjArticleNotFoundException() {
    given(mjArticleRepository.findById(anyLong())).willReturn(Optional.empty());
    thenThrownBy(() -> ReflectionTestUtils.invokeMethod(mjArticleService, "findById", 1L))
        .isExactlyInstanceOf(MjArticleNotFoundException.class);
  }

  @Test
  void createMjArticle_ValidInput_CreatedMjArticle() {
    // given
    MjArticleRequestDto mjArticleRequestDto = getMjArticleRequestDtoFixture();

    MjArticle mjArticleToSave = getMjArticleFixture();
    given(mjArticleRepository.save(any(MjArticle.class))).willReturn(mjArticleToSave);

    // when
    Long id = mjArticleService.createMjArticle(mjArticleRequestDto);

    then(id).isEqualTo(mjArticleToSave.getId());
  }

  @Test
  void readMjArticlePage_ValidInput_ValidOutput() {
    // given
    final Long numMjArticles = 10L;
    List<MjArticle> mjArticles = new ArrayList<>();

    for (long i = 0; i < numMjArticles; i++) {
      mjArticles.add(getMjArticleFixture(i));
    }

    PageImpl<MjArticle> mjArticlePage = new PageImpl<>(mjArticles);
    given(mjArticleRepository.findAll(any(Pageable.class))).willReturn(mjArticlePage);

    // when
    Page<MjArticleResponseDto> mjArticleResponseDtoPage = mjArticleService.readMjArticlePage(
        PageRequest.of(0, numMjArticles.intValue()));

    // expect
    then(mjArticleResponseDtoPage.getTotalElements()).isEqualTo(numMjArticles);
  }

  @Test
  void readMjArticle_ValidInput_FoundMjArticle() {
    MjArticle mjArticle = getMjArticleFixture();
    given(mjArticleRepository.findById(anyLong())).willReturn(Optional.of(mjArticle));

    MjArticleResponseDto foundMjArticle = mjArticleService.readMjArticle(1L);

    then(foundMjArticle)
        .hasFieldOrPropertyWithValue("id", mjArticle.getId())
        .hasFieldOrPropertyWithValue("title", mjArticle.getTitle())
        .hasFieldOrPropertyWithValue("content", mjArticle.getContent());
  }


  @Test
  void updateMjArticle_validInput_validOutput() {
    MjArticleRequestDto mjArticleRequestDto = getMjArticleRequestDtoFixture();
    MjArticle mjArticle = getMjArticleFixture();
    given(mjArticleRepository.findById(any(Long.class))).willReturn(Optional.of(mjArticle));
    given(mjArticleRepository.save(any(MjArticle.class))).willReturn(mjArticle);

    mjArticleService.updateMjArticle(1L, mjArticleRequestDto);
  }

  @Test
  void deleteMjArticle_ValidInput_DeleteMjArticle() {
    // given
    MjArticle mjArticleToDelete = getMjArticleFixture();
    given(mjArticleRepository.findById(any(Long.class))).willReturn(Optional.of(mjArticleToDelete));

    // when
    mjArticleService.deleteById(mjArticleToDelete.getId());
  }

  @Test
  void createEntityFrom_Request_ValidOutput() {
    // given
    MjArticleRequestDto request = getMjArticleRequestDtoFixture();

    // when
    MjArticle mjArticle = ReflectionTestUtils.invokeMethod(mjArticleService, "createEntityFrom", request);
    if (mjArticle == null) {
      throw new RuntimeException();
    }

    // then
    then(mjArticle).hasNoNullFieldsOrPropertiesExcept("id", "createdAt");
    then(mjArticle.getTitle()).isEqualTo(request.getTitle());
    then(mjArticle.getContent()).isEqualTo(request.getContent());
  }

  @Test
  void construct_ValidInput_ValidOutput() {
    // given
    MjArticle mjArticle = getMjArticleFixture();

    // when
    MjArticleResponseDto mjArticleResponseDto = ReflectionTestUtils
        .invokeMethod(mjArticleService, "createResponseDtoFrom", mjArticle);
    if (mjArticleResponseDto == null) {
      throw new RuntimeException();
    }

    // then
    then(mjArticleResponseDto.getId()).isEqualTo(mjArticle.getId());
    then(mjArticleResponseDto.getTitle()).isEqualTo(mjArticle.getTitle());
    then(mjArticleResponseDto.getContent()).isEqualTo(mjArticle.getContent());
    then(mjArticleResponseDto.getCreatedAt()).isEqualTo(mjArticle.getCreatedAt());
  }
}
