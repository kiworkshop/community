package community.content.simplelife.post.api.dto;

import community.common.util.MyReflectionUtils;

public class PostRequestDtoTest {
  public static PostRequestDto getPostRequestDtoFixture() throws Exception {
    PostRequestDto postRequestDto = new PostRequestDto();

    MyReflectionUtils.setField(postRequestDto, "title", "title");
    MyReflectionUtils.setField(postRequestDto, "description", "description");
    MyReflectionUtils.setField(postRequestDto, "content", "content");
    MyReflectionUtils.setField(postRequestDto, "imageUrls", "imageUrls");
    MyReflectionUtils.setField(postRequestDto, "tags", "tags");

    return postRequestDto;
  }
}