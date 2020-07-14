package org.kiworkshop.community.mother.support.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @CsvSource(value = {"title,1", "content,2"})
    void build_EmptyArgument_ThrowException(String emptyField, Long num){
        thenThrownBy(()-> Support.builder()
                .title(emptyField.equals("title") ? "" : "title")
                .content(emptyField.equals("content") ? "" : "content").build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}