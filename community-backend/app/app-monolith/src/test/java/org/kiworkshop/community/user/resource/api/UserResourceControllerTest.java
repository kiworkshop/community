package org.kiworkshop.community.user.resource.api;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.user.resource.domain.service.UserResourceService;
import org.kiworkshop.community.user.resource.dto.UserResourceResponseDto;
import org.kiworkshop.community.user.resource.dto.UserResourceResponseDtoFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserResourceController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class UserResourceControllerTest {
  private static final String USERNAME = "username";

  private @Autowired MockMvc mvc;
  private @MockBean UserResourceService userResourceService;
  private @Autowired ObjectMapper objectMapper;

  @Test
  @WithMockUser(USERNAME)
  void me_ValidInput_ValidOutput() throws Exception {
    // given
    UserResourceResponseDto res = UserResourceResponseDtoFixture.get();
    given(userResourceService.getUserResource(eq(USERNAME))).willReturn(res);

    // expect
    this.mvc.perform(get("/user-resources/me")
        .header("Authorization", "Bearer " + UUID.randomUUID().toString())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(res)))
        .andExpect(status().isOk())
        .andDo(document("user-resources/me",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            responseFields(List.of(
                fieldWithPath("username").description("사용자 고유 username"),
                fieldWithPath("nickname").description("사용자 고유 nickname"),
                fieldWithPath("contactEmail").description("사용자 이메일")
            ))));
  }
}