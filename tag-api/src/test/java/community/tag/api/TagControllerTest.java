package community.tag.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.tag.api.dto.TagContentRequestDto;
import community.tag.api.dto.TagRequestDto;
import community.tag.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static community.tag.api.dto.TagContentRequestDtoTest.getTagContentRequestDtoFixture;
import static community.tag.api.dto.TagRequestDtoTest.getTagRequestDtoFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
public class TagControllerTest {
    private @Autowired MockMvc mvc;
    private @MockBean TagService tagService;
    private @Autowired ObjectMapper objectMapper;

    private FieldDescriptor[] requestFieldDescriptors = new FieldDescriptor[]{
            fieldWithPath("title").description("Title of a notice to create")
                    .attributes(key("constraints").value("Not Empty")),
            fieldWithPath("content").description("Content of a notice to create")
                    .attributes(key("constraints").value("Not Empty, Markdown"))
    };

    private FieldDescriptor[] responseFieldDescriptors = new FieldDescriptor[]{
            fieldWithPath("id").description("Id of a notice"),
            fieldWithPath("title").description("Title of a notice"),
            fieldWithPath("content").description("Content of a notice")
    };

    @Test
    void create_ValidInput_ValidOutput() throws Exception{
        // given
        TagRequestDto tagRequestDto = getTagRequestDtoFixture();
        given(tagService.createTagIfAbsent(any(TagRequestDto.class))).willReturn(1L);

        // expect
        this.mvc.perform(post("/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagRequestDto)))
            .andExpect(status().isOk());
//            .andDo(document("tags/create-a-tag",
//                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
//                    requestFields(requestFieldDescriptors)));
    }

    @Test
    void create_NullFields_StatusBadRequest() throws Exception {
        // given
        TagRequestDto tagRequestDto = new TagRequestDto();

        // expect
        this.mvc.perform(post("/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagRequestDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void get_ValidInput_ValidOutput() throws Exception {
        // given

        // when

        // then
    }


    @Test
    void createTagContents_ValidInput_ValidOutput() throws Exception {
        // given
        TagContentRequestDto tagContentRequestDto = getTagContentRequestDtoFixture();
        given(tagService.createTagContents(any(TagContentRequestDto.class))).willReturn(Arrays.asList(1L, 2L));

        // expect
        this.mvc.perform(post("/tags/tagContents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagContentRequestDto)))
            .andExpect(status().isOk());
    }

}
