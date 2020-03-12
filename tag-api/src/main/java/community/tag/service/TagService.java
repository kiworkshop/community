package community.tag.service;

import community.tag.api.dto.TagContentRequestDto;
import community.tag.api.dto.TagContentResponseDto;
import community.tag.domain.TagContent;
import community.tag.domain.TagContentRepository;
import community.tag.api.dto.TagRequestDto;
import community.tag.api.dto.TagResponseDto;
import community.tag.domain.Tag;
import community.tag.domain.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagContentRepository tagContentRepository;

    public List<TagResponseDto> readAllTags() {
        return tagRepository.findAll().stream()
                        .map(TagResponseDto::of)
                        .collect(Collectors.toList());
    }

    public Long createTag(TagRequestDto tagRequestDto) {
        Tag tag = createTag(tagRequestDto.getName());
        return tagRepository.save(tag).getId();
    }

    public List<TagContentResponseDto> readTagContentsByTag(String tagName) {
        Tag tag = findTagByTagName(tagName);
        return tagContentRepository.findByTag(tag).stream()
                .map(TagContentResponseDto::of)
                .collect(Collectors.toList());
    }

    private Tag findTagByTagName(String tagName) {
        return tagRepository.findByName(tagName).orElseThrow(IllegalArgumentException::new);
    }

    public List<Long> createTagContents(TagContentRequestDto tagContentRequestDto) {
        String tableName = tagContentRequestDto.getTableName();
        Long contentId = tagContentRequestDto.getContentId();
        List<TagContent> tagContents = tagContentRequestDto.getTagNames().stream()
                .map(this::createTag)
                .map(tag -> createContentTable(tag, tableName, contentId))
                .collect(Collectors.toList());

        return tagContentRepository.saveAll(tagContents).stream()
                .map(TagContent::getId)
                .collect(Collectors.toList());
    }

    private Tag createTag(String name) {
        return tagRepository.findByName(name)
                .orElse(Tag.of(name));
    }

    private TagContent createContentTable(Tag tag, String contentType, Long contentId) {
        return TagContent.builder().tag(tag)
                .contentType(contentType)
                .contentId(contentId).build();
    }

    public List<TagResponseDto> readTagsByTagContent(String contentType, Long contentId) {
        List<TagContent> tagContents = tagContentRepository.findByContentTypeAndContentId(contentType, contentId);

        return tagContents.stream()
                .map(TagContent::getTag)
                .map(TagResponseDto::of)
                .collect(Collectors.toList());
    }
}
