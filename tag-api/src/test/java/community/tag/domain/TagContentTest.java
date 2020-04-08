package community.tag.domain;

import org.junit.jupiter.api.Test;

import static community.tag.domain.TagTest.getTagFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class TagContentTest {

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