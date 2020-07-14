package org.kiworkshop.community.content.exception;

import org.kiworkshop.community.common.dto.ApiError;
import org.kiworkshop.community.common.exception.CommonExceptionController;
import org.kiworkshop.community.content.mjarticle.exception.MjArticleNotFoundException;
import org.kiworkshop.community.content.myanglog.exception.MyangPostNotFoundException;
import org.kiworkshop.community.content.simplelife.article.exception.SimpleArticleNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionController extends CommonExceptionController {

  @Override
  @ExceptionHandler(value = {
      MjArticleNotFoundException.class,
      MyangPostNotFoundException.class,
      SimpleArticleNotFoundException.class
  })
  public ApiError handleNotFound(RuntimeException e) {
    return super.handleNotFound(e);
  }

  @Override
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiError handleInvalidParam(MethodArgumentNotValidException e) {
    return super.handleInvalidParam(e);
  }
}
