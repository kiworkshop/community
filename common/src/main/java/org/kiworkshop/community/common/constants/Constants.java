package org.kiworkshop.community.common.constants;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;

public class Constants {
  private Constants() {}

  public static final FieldDescriptor[] pageResponseFieldDescriptors = {
      fieldWithPath("pageable").description("The Pageable that's been used to request the current Slice.").optional(),
      fieldWithPath("pageable.sort").ignored(),
      fieldWithPath("pageable.sort.sorted").ignored(),
      fieldWithPath("pageable.sort.unsorted").ignored(),
      fieldWithPath("pageable.sort.empty").ignored(),
      fieldWithPath("pageable.offset").ignored(),
      fieldWithPath("pageable.pageNumber").ignored(),
      fieldWithPath("pageable.pageSize").ignored(),
      fieldWithPath("pageable.paged").ignored(),
      fieldWithPath("pageable.unpaged").ignored(),
      fieldWithPath("totalElements").description("The number of total creators"),
      fieldWithPath("totalPages").description("Total count of pages"),
      fieldWithPath("size").description("The size of current page"),
      fieldWithPath("number").description("The number of the current page"),
      fieldWithPath("numberOfElements").description("The number of creators currently on this page."),
      fieldWithPath("sort").description("The sorting parameters for the page."),
      fieldWithPath("sort.sorted").description("Whether the current page is sorted"),
      fieldWithPath("sort.unsorted").description("Whether the current page is unsorted"),
      fieldWithPath("sort.empty").description("Whether sort order is empty"),
      fieldWithPath("first").description("Whether the current page is the first one"),
      fieldWithPath("last").description("Whether the current page is the last one"),
      fieldWithPath("empty").description("Whether the contents are empty"),
      fieldWithPath("content[]").description("Contents of a page")
  };
}
