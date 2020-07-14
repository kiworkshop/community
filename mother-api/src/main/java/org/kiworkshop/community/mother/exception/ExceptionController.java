package org.kiworkshop.community.mother.exception;

import org.kiworkshop.community.common.dto.ApiError;
import org.kiworkshop.community.common.exception.CommonExceptionController;
import org.kiworkshop.community.mother.notice.exception.NoticeNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionController extends CommonExceptionController {

  @Override
  @ExceptionHandler(NoticeNotFoundException.class)
  public ApiError handleNotFound(RuntimeException e) {
    return super.handleNotFound(e);
  }

  @Override
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiError handleInvalidParam(MethodArgumentNotValidException e) {
    return super.handleInvalidParam(e);
  }
}
