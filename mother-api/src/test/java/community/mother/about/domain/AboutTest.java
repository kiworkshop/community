package community.mother.about.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class /**/AboutTest {

    public static About getAboutFixture() throws Exception{
        return About.builder()
                .title("title")
                .content("content").build();
    }

    @Test
    void build_ValidInput_ValidOutput(){

        //given
        About about = About.builder()
                .title("title")
                .content("content").build();
        //then
        then(about)
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("content","content");

    }

    @ParameterizedTest
    @CsvSource(value = {"title,1", "content,2"})
    void build_EmptyArgument_ThrowException(String emptyField, Long num){
        thenThrownBy(()-> About.builder()
                .title(emptyField.equals("title") ? "" : "title")
                .content(emptyField.equals("content") ? "" : "content").build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
