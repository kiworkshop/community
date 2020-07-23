package org.kiworkshop.community.content.simplelife.exception;

import org.kiworkshop.community.common.dto.ApiError;
import org.kiworkshop.community.common.exception.CommonExceptionController;
import org.kiworkshop.community.content.simplelife.article.exception.SimpleArticleNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionController extends CommonExceptionController {
  @Override
  @ExceptionHandler(SimpleArticleNotFoundException.class)
  public ApiError handleNotFound(RuntimeException e) {
    return super.handleNotFound(e);
  }
}
