package community.tag.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class TagTest {
    public static List<Tag> getTagsFixture() {
        return Stream.of(
                Tag.of("tag1"),
                Tag.of("tag2")
        ).collect(Collectors.toList());
    }

    public static Tag getTagFixture() {
        return Tag.of("tag");
    }

    public static Tag getTagFixture(String name) {
        return Tag.of(name);
    }

    @Test
    void of_ValidInput_ValidOutput() {
        // when
        Tag tag = Tag.of("tag");

        // then
        then(tag).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt")
                .hasFieldOrPropertyWithValue("name", "tag");
    }

    @Test
    void of_InvalidInput_ThrowException() {
        // given
        thenThrownBy(() ->
                Tag.of("")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}