package community.tag.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.tag.api.dto.TagContentRequestDto;
import community.tag.api.dto.TagRequestDto;
import community.tag.api.dto.TagResponseDto;
import community.tag.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static community.common.constants.Constants.pageResponseFieldDescriptors;
import static community.tag.api.dto.TagContentRequestDtoTest.getTagContentRequestDtoFixture;
import static community.tag.api.dto.TagRequestDtoTest.getTagRequestDtoFixture;
import static community.tag.api.dto.TagResponseDtoTest.getTagResponseFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
public class TagControllerTest {
    private @Autowired MockMvc mvc;
    private @MockBean TagService tagService;
    private @Autowired ObjectMapper objectMapper;

    private FieldDescriptor[] requestFieldDescriptors = new FieldDescriptor[]{
            fieldWithPath("name").description("Name of a tag to create")
                    .attributes(key("constraints").value("Not Empty"))
    };

    private FieldDescriptor[] responseFieldDescriptors = new FieldDescriptor[]{
            fieldWithPath("name").description("Name of a tag")
    };

    private FieldDescriptor[] tagContentRequestFieldDescriptors = new FieldDescriptor[] {
            fieldWithPath("tagNames").description("Names of tag")
                    .attributes(key("constraints").value("Not Empty")),
            fieldWithPath("contentType").description("Content Type of a tag")
                    .attributes(key("constraints").value("Not Empty")),
            fieldWithPath("contentId").description("Content Id of a tag")
                    .attributes(key("constraints").value("Not Empty"))
    };

    @Test
    void readAll_ValidInput_ValidOutput() throws Exception {
        // given
        List<TagResponseDto> tagResponseDtos = new ArrayList<>();
        final var numTags = 10L;
        String tagName = "tag";
        for (long i = 0; i < numTags; i++) {
            tagResponseDtos.add(getTagResponseFixture(tagName.concat(Long.toString(i))));
        }
        Collections.reverse(tagResponseDtos);
        PageImpl<TagResponseDto> tagResponseDtoPage = new PageImpl<>(
                tagResponseDtos,
                PageRequest.of(0, 10, Sort.Direction.DESC, "id"),
                10);
        given(tagService.readTagPage(any(Pageable.class))).willReturn(tagResponseDtoPage);

        // expect
        this.mvc.perform(get("/tags"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
            .andExpect(jsonPath("$.pageable.pageSize").value(10))
            .andDo(document("tags/read-tags",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    requestParameters(
                        parameterWithName("page").description("The page to retrieve").optional()
                            .attributes(key("constraints").value("Nullable, Default: 1")),
                        parameterWithName("size").description("Entries size per page").optional()
                            .attributes(key("constraints").value("Nullable, Default: 10")),
                        parameterWithName("sort")
                            .description("Sorting option. format -> '{columnName},{asc|desc}'").optional()
                            .attributes(key("constraints").value("Nullable, Default: id,desc"))),
                    responseFields(pageResponseFieldDescriptors)
                        .andWithPrefix("content[].", responseFieldDescriptors)));
    }

    @Test
    void create_ValidInput_ValidOutput() throws Exception{
        // given
        TagRequestDto tagRequestDto = getTagRequestDtoFixture();
        given(tagService.createTagIfAbsent(any(TagRequestDto.class))).willReturn(1L);

        // expect
        this.mvc.perform(post("/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("tags/create-a-tag",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    requestFields(requestFieldDescriptors)));
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
    void createTagContents_ValidInput_ValidOutput() throws Exception {
        // given
        TagContentRequestDto tagContentRequestDto = getTagContentRequestDtoFixture();
        given(tagService.createTagContents(any(TagContentRequestDto.class))).willReturn(Arrays.asList(1L, 2L));

        // expect
        this.mvc.perform(post("/tags/tagContents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagContentRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("tags/create-tag-contents",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(tagContentRequestFieldDescriptors)));
    }
}
