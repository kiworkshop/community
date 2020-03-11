package community.mother.support.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class SupportTest {

    public static Support getSupportFixture(){
        return Support.builder().title("title").content("content").build();
    }

    @Test
    void build_ValidInput_ValidOutput(){
        Support support = Support.builder().title("title").content("content").build();

        then(support)
                .hasFieldOrPropertyWithValue("title","title")
                .hasFieldOrPropertyWithValue("content","content");
    }

    @Test
    void build_EmptyTitle_ThrownException(){

        thenThrownBy(()->
                Support.builder()
                        .title("")
                        .content("content")
                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }
}