package community.tag.api;

import community.tag.api.dto.TagContentRequestDto;
import community.tag.api.dto.TagContentResponseDto;
import community.tag.api.dto.TagRequestDto;
import community.tag.api.dto.TagResponseDto;
import community.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public Page<TagResponseDto> readTagPage(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return tagService.readTagPage(pageable);
    }

    @PostMapping
    public Long create(@RequestBody @Valid TagRequestDto tagRequestDto) {
        return tagService.createTagIfAbsent(tagRequestDto);
    }

    @GetMapping("/{contentType}/{contentId}")
    public List<TagResponseDto> readTags(@PathVariable String contentType, @PathVariable Long contentId) {
        return tagService.readTagsByTagContent(contentType, contentId);
    }

    @GetMapping("/{tagName}")
    public List<TagContentResponseDto> readTagContents(@PathVariable String tagName) {
        return tagService.readTagContentsByTag(tagName);
    }

    @PostMapping("/tagContents")
    public List<Long> createTagContents(@RequestBody @Valid TagContentRequestDto tagContentRequestDto) {
        return tagService.createTagContents(tagContentRequestDto);
    }
}
