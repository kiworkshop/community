package community.tag.domain;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static community.tag.domain.TagTest.getTagFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

public class TagContentTest {
    public static TagContent getTagContentsFixture(Long tagContentId, String tagName) {
        Tag tag = getTagFixture(tagName);
        TagContent tagContent = TagContent.builder()
                    .tag(tag)
                    .contentType("simplelife")
                    .contentId(1L).build();
        ReflectionTestUtils.setField(tagContent, "id", tagContentId);
        return tagContent;
    }

    public static List<TagContent> getTagContentsFixture(Tag tag) {
        return Stream.of(
                TagContent.builder().tag(tag).contentType("simplelife").contentId(1L).build(),
                TagContent.builder().tag(tag).contentType("mjarticle").contentId(1L).build()
        ).collect(Collectors.toList());
    }

    @Test
    void build_ValidInput_ValidOutput() {
        // given
        Tag tag = getTagFixture();
        String contentType = "simplelife";
        Long contentId = 1L;

        // when
        TagContent tagContent = TagContent.builder()
                .tag(tag)
                .contentType(contentType)
                .contentId(contentId).build();

        // then
        then(tagContent).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt")
                .hasFieldOrPropertyWithValue("tag", tag)
                .hasFieldOrPropertyWithValue("contentType", contentType)
                .hasFieldOrPropertyWithValue("contentId", contentId);
    }

   @Test
   void build_NullTag_ThrowException() {
       // then
       thenThrownBy(() ->
               TagContent.builder()
                .tag(null)
                .contentType("simplelife")
                .contentId(1L).build()
       ).isInstanceOf(IllegalArgumentException.class);
   }


    @Test
    void build_EmptyContentType_ThrowException() {
        // given
        Tag tag = getTagFixture();

        // then
        thenThrownBy(() ->
                TagContent.builder()
                        .tag(tag)
                        .contentType("")
                        .contentId(1L).build()
        ).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void build_NullContentId_ThrowException() {
        // given
        Tag tag = getTagFixture();

        // then
        thenThrownBy(() ->
                TagContent.builder()
                        .tag(tag)
                        .contentType("simplelife")
                        .contentId(null).build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}