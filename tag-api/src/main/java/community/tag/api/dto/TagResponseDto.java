package community.tag.api.dto;

import community.tag.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagResponseDto {
    private String name;

    private TagResponseDto(Tag tag) {
        this.name = tag.getName();
    }

    public static TagResponseDto from(Tag tag) {
        return new TagResponseDto(tag);
    }
}
