package community.mother.notice.api;

import static community.common.constants.Constants.pageResponseFieldDescriptors;
import static community.mother.notice.api.dto.NoticeRequestDtoTest.getNoticeRequestDtoFixture;
import static community.mother.notice.api.dto.NoticeResponseDtoTest.getNoticeResponseDtoFixture;
import static community.mother.notice.api.dto.NoticeResponseDtoTest.getNoticeResponseFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.mother.notice.api.dto.NoticeRequestDto;
import community.mother.notice.api.dto.NoticeResponseDto;
import community.mother.notice.exception.NoticeNotFoundException;
import community.mother.notice.service.NoticeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

@WebMvcTest(NoticeController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class NoticeControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean NoticeService noticeService;
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
  void createNotice_ValidInput_ValidOutput() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = getNoticeRequestDtoFixture();
    given(noticeService.createNotice(any(NoticeRequestDto.class))).willReturn(1L);

    // expect
    this.mvc.perform(post("/notices")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1L))
        .andDo(document("notices/create-a-notice",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            requestFields(requestFieldDescriptors)));
  }

  @Test
  void createNotice_NullFields_StatusBadRequest() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = new NoticeRequestDto();

    // expect
    this.mvc.perform(post("/notices")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void readNotices_ValidInput_ValidOutput() throws Exception {
    // given
    List<NoticeResponseDto> noticeResponseDtos = new ArrayList<>();
    final var numNotices = 10L;
    for (long i = 0; i < numNotices; i++) {
      noticeResponseDtos.add(getNoticeResponseDtoFixture(i + 1));
    }
    Collections.reverse(noticeResponseDtos);
    PageImpl<NoticeResponseDto> noticeResponseDtoPage = new PageImpl<>(
        noticeResponseDtos,
        PageRequest.of(0, 10, Sort.Direction.DESC, "id"),
        10);
    given(noticeService.readNoticePage(any(Pageable.class))).willReturn(noticeResponseDtoPage);

    // expect
    this.mvc.perform(get("/notices"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(10))
        .andExpect(jsonPath("$.content[9].id").value(1))
        .andExpect(jsonPath("$.pageable.sort.sorted").value(true))
        .andExpect(jsonPath("$.pageable.pageSize").value(10))
        .andDo(document("notices/read-notices",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            requestParameters(
                parameterWithName("page").description("The page to retrieve").optional()
                    .attributes(key("constraints").value("Nullable, Default: 1")),
                parameterWithName("size").description("Entries size per page").optional()
                    .attributes(key("constraints").value("Nullable, Default: 10")),
                parameterWithName("sort")
                    .description("Sorting option. format -> '{columnName},(asc|desc)'").optional()
                    .attributes(key("constraints").value("Nullable, Default: id,desc"))),
            responseFields(pageResponseFieldDescriptors)
                .andWithPrefix("content[].", responseFieldDescriptors)));
  }

  @Test
  void get_ValidInput_NoticeResponse() throws Exception {
    NoticeResponseDto noticeResponseDto = getNoticeResponseFixture();
    given(noticeService.readNotice(anyLong())).willReturn(noticeResponseDto);

    this.mvc.perform(get("/notices/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(1L))
        .andExpect(jsonPath("title").value("title"))
        .andExpect(jsonPath("content").value("content"))
        .andDo(document("notices/read-a-notice",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            pathParameters(parameterWithName("id")
                .description("An id of a notice to get.")
                .attributes(key("constraints").value("Not Null"))),
            responseFields(responseFieldDescriptors)));
  }

  @Test
  void get_NonExistentId_ApiError() throws Exception {
    given(noticeService.readNotice(anyLong())).willThrow(new NoticeNotFoundException(1L));

    this.mvc.perform(get("/notices/{id}", 1))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status").isNumber())
        .andExpect(jsonPath("error").isNotEmpty())
        .andExpect(jsonPath("message").isNotEmpty());
  }

  @Test
  void updateNotice_ValidInput_ValidOutput() throws Exception {
    // given
    NoticeRequestDto noticeRequestDto = getNoticeRequestDtoFixture();

    // expect
    this.mvc.perform(put("/notices/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andDo(document("notices/update-a-notice",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            pathParameters(parameterWithName("id")
                .description("An id of a notice to update.")
                .attributes(key("constraints").value("Not Null"))),
            requestFields(requestFieldDescriptors)));
  }

  @Test
  void deleteNotice_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(delete("/notices/{id}", 1L))
        .andExpect(status().isOk())
        .andDo(document("notices/delete-a-notice",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            pathParameters(parameterWithName("id")
                .description("An id of a notice to delete.")
                .attributes(key("constraints").value("Not Null")))));
    ;
  }
}
