package community.tag.api.dto;

import static community.tag.domain.TagTest.getTagFixture;

public class TagResponseDtoTest {
    public static TagResponseDto getTagResponseFixture() throws Exception {
        return TagResponseDto.from(getTagFixture());
    }
}
