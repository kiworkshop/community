package community.tag.service;

import community.tag.api.dto.TagContentRequestDto;
import community.tag.api.dto.TagContentResponseDto;
import community.tag.api.dto.TagRequestDto;
import community.tag.api.dto.TagResponseDto;
import community.tag.domain.Tag;
import community.tag.domain.TagContent;
import community.tag.domain.TagContentRepository;
import community.tag.domain.TagRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static community.tag.api.dto.TagContentRequestDtoTest.getTagContentRequestDtoFixture;
import static community.tag.api.dto.TagRequestDtoTest.getTagRequestDtoFixture;
import static community.tag.domain.TagContentTest.getTagContentsFixture;
import static community.tag.domain.TagTest.getTagFixture;
import static community.tag.domain.TagTest.getTagsFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    private TagService tagService;

    private @Mock TagRepository tagRepository;
    private @Mock TagContentRepository tagContentRepository;

    @BeforeEach
    void setUp() {
        tagService = new TagService(tagRepository, tagContentRepository);
    }

    @Test
    void readAllTags_ValidInput_ValidOutput() {
        // given
        final Long numTags = 10L;
        String tagName = "tag";
        List<Tag> tags = new ArrayList<>();

        for (long i = 0; i < numTags; i++) {
            tags.add(getTagFixture(tagName.concat(Long.toString(i))));
        }

        PageImpl<Tag> tagPage = new PageImpl<>(tags);
        given(tagRepository.findAll(any(Pageable.class))).willReturn(tagPage);

        // when
        Page<TagResponseDto> tagResponseDtoPage = tagService.readTagPage(
                PageRequest.of(0, numTags.intValue())
        );

        // then
        then(tagResponseDtoPage.getTotalElements()).isEqualTo(numTags);
    }

    @Test
    void createTagIfAbsent_ValidTagInput_ValidOutput() {
        // given
        TagRequestDto tagRequestDto = getTagRequestDtoFixture();
        Tag tag = Tag.of(tagRequestDto.getName());
        ReflectionTestUtils.setField(tag, "id", 1L);
        given(tagRepository.findByName(any(String.class))).willReturn(Optional.of(tag));
        given(tagRepository.save(any(Tag.class))).willReturn(tag);

        // when
        Long tagId = tagService.createTagIfAbsent(tagRequestDto);

        // then
        then(tagId).isEqualTo(tag.getId());
        then(tag.getName()).isEqualTo(tagRequestDto.getName());
    }

    @Test
    void createTagIfAbsent_AbsentTagInput_ValidOutput() {
        // given
        TagRequestDto tagRequestDto = getTagRequestDtoFixture();
        Tag tag = Tag.of(tagRequestDto.getName());
        ReflectionTestUtils.setField(tag, "id", 1L);
        given(tagRepository.findByName(any(String.class))).willReturn(Optional.empty());
        given(tagRepository.save(any(Tag.class))).willReturn(tag);

        // when
        Long tagId = tagService.createTagIfAbsent(tagRequestDto);

        // then
        then(tagId).isEqualTo(tag.getId());
    }

    @Test
    void readTagContentsByTag_ValidInput_ValidOutput() {
        // given
        Tag tag = getTagFixture();
        List<TagContent> tagContents = getTagContentsFixture(tag);

        given(tagRepository.findByName(any(String.class))).willReturn(Optional.of(tag));
        given(tagContentRepository.findByTag(any(Tag.class))).willReturn(tagContents);

        // when
        List<TagContentResponseDto> tagContentResponseDtos = tagService.readTagContentsByTag(tag.getName());

        // then
        then(tagContentResponseDtos.get(0).getContentType()).isEqualTo(tagContents.get(0).getContentType());
        then(tagContentResponseDtos.get(0).getContentId()).isEqualTo(tagContents.get(0).getContentId());

        // and then
        then(tagContentResponseDtos.get(1).getContentType()).isEqualTo(tagContents.get(1).getContentType());
        then(tagContentResponseDtos.get(1).getContentId()).isEqualTo(tagContents.get(1).getContentId());
    }

    @Test
    void createTagContents_ValidInput_ValidOutput() {
        // given
        TagContentRequestDto tagContentRequestDto = getTagContentRequestDtoFixture();
        List<TagContent> tagContentsToSave = Stream.of(
                getTagContentsFixture(1L, "tag1"),
                getTagContentsFixture(2L, "tag2")
        ).collect(Collectors.toList());
        given(tagContentRepository.saveAll(any())).willReturn(tagContentsToSave);

        // when
        List<Long> tagContentIds = tagService.createTagContents(tagContentRequestDto);

        // then
        then(tagContentIds).isEqualTo(Arrays.asList(1L, 2L));
    }

    // Todo
    @Test
    void readTagsByTagContent_ValidInput_ValidOutput() {
        // given

        // when

        // then
    }

}
