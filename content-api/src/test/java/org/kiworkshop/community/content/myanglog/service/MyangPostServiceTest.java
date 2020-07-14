package org.kiworkshop.community.content.myanglog.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.common.config.CommonBeanCreators;
import org.kiworkshop.community.content.myanglog.api.dto.MyangPostRequestDto;
import org.kiworkshop.community.content.myanglog.api.dto.MyangPostRequestDtoTest;
import org.kiworkshop.community.content.myanglog.api.dto.MyangPostResponseDto;
import org.kiworkshop.community.content.myanglog.domain.MyangPost;
import org.kiworkshop.community.content.myanglog.domain.MyangPostRepository;
import org.kiworkshop.community.content.myanglog.domain.MyangPostTest;
import org.kiworkshop.community.content.myanglog.domain.MyangTagRepository;
import org.kiworkshop.community.content.myanglog.exception.MyangPostNotFoundException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyangPostServiceTest {
  private MyangPostService myangPostService;

  private @Mock MyangPostRepository myangPostRepository;
  private @Mock MyangTagRepository myangTagRepository;

  @BeforeEach
  void setUp() {
    myangPostService = new MyangPostService(myangPostRepository, myangTagRepository, CommonBeanCreators.modelMapper());
  }

  @Test
  void readPost_ValidInput_ValidOutput() throws Exception {
    // given
    MyangPost myangPost = MyangPostTest.getMyangPostFixture();
    given(myangPostRepository.findById(anyLong())).willReturn(Optional.of(myangPost));
    // when
    MyangPostResponseDto myangPostResponseDto = myangPostService.readPost(1L);
    // expect
    then(myangPostResponseDto)
        .hasFieldOrPropertyWithValue("id", myangPost.getId())
        .hasFieldOrPropertyWithValue("title", myangPost.getTitle())
        .hasFieldOrPropertyWithValue("content", myangPost.getContent())
        .hasFieldOrPropertyWithValue("likeCount", myangPost.getLikeCount())
        .hasFieldOrPropertyWithValue("viewCount", myangPost.getViewCount());
  }

  @Test
  void readPost_NonExistentIdInput_ThrowPostNotFoundException() {
    given(myangPostRepository.findById(anyLong())).willReturn(Optional.empty());
    thenThrownBy(() -> myangPostService.readPost(1L)).isInstanceOf(MyangPostNotFoundException.class);
  }

  @Test
  void createPost_ValidInput_CreatedPostId() throws Exception {
    MyangPostRequestDto request = MyangPostRequestDtoTest.getMyangPostRequestDtoFixture();
    MyangPost myangPostToSave = MyangPostTest.getMyangPostFixture();
    given(myangPostRepository.save(any(MyangPost.class))).willReturn(myangPostToSave);

    Long id = myangPostService.createPost(request);

    then(id).isEqualTo(myangPostToSave.getId());
  }

  @Test
  void updatePost_ValidInput_postUpdated() throws Exception {
    MyangPostRequestDto request = MyangPostRequestDtoTest.getMyangPostRequestDtoFixture();
    MyangPost myangPost = MyangPostTest.getMyangPostFixture();
    given(myangPostRepository.findById(any(Long.class))).willReturn(Optional.of(myangPost));
    given(myangPostRepository.save(any(MyangPost.class))).willReturn(myangPost);

    myangPostService.updatePost(1L, request);
  }

  @Test
  void deletePost_ValidInput_postDeleted() throws Exception {
    MyangPost myangPost = MyangPostTest.getMyangPostFixture();
    given(myangPostRepository.findById(any(Long.class))).willReturn(Optional.of(myangPost));

    myangPostService.deletePost(myangPost.getId());
  }
}
