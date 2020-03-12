package community.tag.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class TagTest {
    @Test
    void of_ValidInput_ValidOutput() {
        // given
        String name = "tag";
        // when
        Tag tag = Tag.of(name);
        // then
        assertThat(tag.getName()).isEqualTo(name);
    }

    @Test
    void of_InvalidInput_ThrowException() {
        // given
        String emptyName = "";
        // then
        assertThatThrownBy(() -> Tag.of(emptyName)).isInstanceOf(IllegalArgumentException.class);
    }
}