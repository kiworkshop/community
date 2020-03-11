package community.mother.about.domain;

import org.junit.jupiter.api.Test;

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

    @Test
    void build_EmptyTitle_ThrowException(){

        thenThrownBy(()->
                About.builder()
                .title("")
                .content("content").build()
        ).isInstanceOf(IllegalArgumentException.class);

    }
}
