package community.tag.api.dto;

import static community.tag.domain.TagTest.getTagFixture;

public class TagResponseDtoTest {
    public static TagResponseDto getTagResponseFixture(String name) throws Exception {
        return TagResponseDto.from(getTagFixture(name));
    }
}
