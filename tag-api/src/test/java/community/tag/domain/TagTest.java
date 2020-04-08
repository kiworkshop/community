package community.tag.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class TagTest {

    public static Tag getTagFixture() {
        return Tag.of("tag");
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