package community.tag.api.dto;

import community.tag.domain.TagContent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagContentResponseDto {
    private String contentType;
    private Long contentId;

    private TagContentResponseDto(TagContent tagContent) {
        this.contentType = tagContent.getContentType();
        this.contentId = tagContent.getContentId();
    }

    public static TagContentResponseDto from(TagContent tagContent) {
        return new TagContentResponseDto(tagContent);
    }
}
