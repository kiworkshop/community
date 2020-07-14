package org.kiworkshop.community.comment.api;

import static org.kiworkshop.community.comment.api.dto.CommentRequestDtoTest.getCommentRequestDtoFixture;
import static org.kiworkshop.community.comment.api.dto.CommentResponseDtoTest.getCommentResponseDtoFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.common.model.BoardType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.comment.api.dto.CommentRequestDto;
import org.kiworkshop.community.comment.api.dto.CommentResponseDto;
import org.kiworkshop.community.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
public class CommentControllerTest {
  private @Autowired MockMvc mvc;
  private @MockBean CommentService commentService;
  private @Autowired ObjectMapper objectMapper;

  private FieldDescriptor[] requestFieldDescriptors = new FieldDescriptor[]{
      fieldWithPath("boardType").description("board to add comment")
          .attributes(key("constraints").value("Not null")),
      fieldWithPath("postId").description("post id to add comment")
          .attributes(key("constraints").value("Not null")),
      fieldWithPath("username").description("user name")
          .attributes(key("constraints").value("Not Empty, Markdown")),
      fieldWithPath("content").description("Content of a comment to create")
          .attributes(key("constraints").value("Not Empty, Markdown")),
      fieldWithPath("parentId").description("Parent Id of a comment to create"),
      fieldWithPath("order").description("order of a comment to create")
  };

  private FieldDescriptor[] responseFieldDescriptors = new FieldDescriptor[]{
      fieldWithPath("[].id").description("Id of a comment"),
      fieldWithPath("[].username").description("User name of a comment"),
      fieldWithPath("[].content").description("Content of a comment"),
      fieldWithPath("[].parentId").description("Parent Id of a comment"),
      fieldWithPath("[].order").description("Order of a comment"),
      fieldWithPath("[].active").description("Whether a comment is active"),
      fieldWithPath("[].children").description("Nested comments of a comment"),
      fieldWithPath("[].createdAt").description("Created date time")
  };

  @Test
  void get_ValidInput_ValidOutput() throws Exception {
    // given
    List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
    long numOfComments = 2L;
    for (long i = 0; i < numOfComments; i++) {
      commentResponseDtos.add(getCommentResponseDtoFixture(i + 1));
    }
    given(commentService.getComments(eq(BoardType.NOTICE), any(Long.class))).willReturn(commentResponseDtos);

    // expect
    this.mvc.perform(get("/comments/{boardType}/{postId}", BoardType.NOTICE, 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[0].active").value(true))
        .andExpect(jsonPath("$[0].username").value("user1"))
        .andExpect(jsonPath("$[0].content").value("content"))
        .andDo(document("comments/read-comments",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("boardType").description("(enum) board type: NOTICE, JGRAPHY, MJARTICLE, MYANGLOG, SIMPLELIFE"),
                parameterWithName("postId").description("id of a post that comment is added")
            ),
            responseFields(responseFieldDescriptors)));
  }

  @Test
  void post_ValidInput_ValidOutput() throws Exception {
    // given
    CommentRequestDto commentRequestDto = getCommentRequestDtoFixture();

    // expect
    this.mvc.perform(post("/comments")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(commentRequestDto)))
        .andExpect(status().isOk())
        .andDo(document("comments/create-a-comment",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            requestFields(requestFieldDescriptors)));
  }

  @Test
  void post_NullFields_StatusBadRequest() throws Exception {
    // given
    CommentRequestDto commentRequestDto = new CommentRequestDto();

    // expect
    this.mvc.perform(post("/comments")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(commentRequestDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void put_ValidInput_ValidOutput() throws Exception {
    // given
    CommentRequestDto commentRequestDto = getCommentRequestDtoFixture();

    // expect
    this.mvc.perform(put("/comments/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(commentRequestDto)))
        .andDo(document("comments/update-a-comment",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            pathParameters(parameterWithName("id")
                .description("An id of a comment to update.")
                .attributes(key("constraints").value("Not Null"))),
            requestFields(requestFieldDescriptors)));
  }

  @Test
  void delete_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(delete("/comments/{id}", 1L))
        .andExpect(status().isOk())
        .andDo(document("comments/delete-a-comment",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            pathParameters(parameterWithName("id")
                .description("An id of a comment to delete.")
                .attributes(key("constraints").value("Not Null")))));
  }
}