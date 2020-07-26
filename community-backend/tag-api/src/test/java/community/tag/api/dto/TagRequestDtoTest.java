package community.tag.api.dto;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

public class TagRequestDtoTest {

    public static List<TagRequestDto> getTagRequestDtosFixture() {
        TagRequestDto tagRequestDto1 = new TagRequestDto();
        TagRequestDto tagRequestDto2 = new TagRequestDto();

        ReflectionTestUtils.setField(tagRequestDto1, "name", "tag1");
        ReflectionTestUtils.setField(tagRequestDto2, "name", "tag2");

        return Arrays.asList(tagRequestDto1, tagRequestDto2);
    }

    public static TagRequestDto getTagRequestDtoFixture() {
        TagRequestDto tagRequestDto = new TagRequestDto();

        ReflectionTestUtils.setField(tagRequestDto, "name", "tag");

        return tagRequestDto;
    }
}
