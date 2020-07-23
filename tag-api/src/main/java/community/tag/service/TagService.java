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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagContentRepository tagContentRepository;

    public Page<TagResponseDto> readTagPage(Pageable pageable) {
        return tagRepository.findAll(pageable).map(TagResponseDto::from);
    }

    public Long createTagIfAbsent(TagRequestDto tagRequestDto) {
        Tag tag = createTagIfAbsent(tagRequestDto.getName());
        return tagRepository.save(tag).getId();
    }

    public List<TagContentResponseDto> readTagContentsByTag(String tagName) {
        Tag tag = findTagByTagName(tagName);
        return tagContentRepository.findByTag(tag).stream()
                .map(TagContentResponseDto::from)
                .collect(Collectors.toList());
    }

    private Tag findTagByTagName(String tagName) {
        return tagRepository.findByName(tagName).orElseThrow(IllegalArgumentException::new);
    }

    public List<Long> createTagContents(TagContentRequestDto tagContentRequestDto) {
        String contentType = tagContentRequestDto.getContentType();
        Long contentId = tagContentRequestDto.getContentId();
        List<TagContent> tagContents = tagContentRequestDto.getTagNames().stream()
                .map(this::createTagIfAbsent)
                .map(tag -> createTagContent(tag, contentType, contentId))
                .collect(Collectors.toList());

        return tagContentRepository.saveAll(tagContents).stream()
                .map(TagContent::getId)
                .collect(Collectors.toList());
    }

    private Tag createTagIfAbsent(String name) {
        return tagRepository.findByName(name)
                .orElse(Tag.of(name));
    }

    private TagContent createTagContent(Tag tag, String contentType, Long contentId) {
        return TagContent.builder().tag(tag)
                .contentType(contentType)
                .contentId(contentId).build();
    }

    public List<TagResponseDto> readTagsByTagContent(String contentType, Long contentId) {
        List<TagContent> tagContents = tagContentRepository.findByContentTypeAndContentId(contentType, contentId);

        return tagContents.stream()
                .map(TagContent::getTag)
                .map(TagResponseDto::from)
                .collect(Collectors.toList());
    }
}
