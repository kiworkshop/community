package community.tag.api.dto;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

public class TagContentRequestDtoTest {

    public static TagContentRequestDto getTagContentRequestDtoFixture() {
        TagContentRequestDto tagContentRequestDto = new TagContentRequestDto();
        List<String> tagNames = Arrays.asList("tag1", "tag2");

        ReflectionTestUtils.setField(tagContentRequestDto, "tagNames", tagNames);
        ReflectionTestUtils.setField(tagContentRequestDto, "contentType", "simpelife");
        ReflectionTestUtils.setField(tagContentRequestDto, "contentId", 1L);

        return tagContentRequestDto;
    }

}