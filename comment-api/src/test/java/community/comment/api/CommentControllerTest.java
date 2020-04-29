package community.comment.api;

import static community.comment.api.dto.CommentRequestDtoTest.getCommentRequestDtoFixture;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.comment.api.dto.CommentRequestDto;
import community.comment.service.CommentService;
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
      fieldWithPath("id").description("Id of a comment"),
      fieldWithPath("content").description("Content of a comment")
  };

  @Test
  void createComment_ValidInput_ValidOutput() throws Exception {
    // given
    CommentRequestDto noticeRequestDto = getCommentRequestDtoFixture();

    // expect
    this.mvc.perform(post("/comments")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noticeRequestDto)))
        .andExpect(status().isOk())
        .andDo(document("comments/create-a-comment",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            requestFields(requestFieldDescriptors)));
  }

  @Test
  void createComment_NullFields_StatusBadRequest() throws Exception {
    // given
    CommentRequestDto commentRequestDto = new CommentRequestDto();

    // expect
    this.mvc.perform(post("/comments")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(commentRequestDto)))
        .andExpect(status().isBadRequest());
  }



}