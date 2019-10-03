package community.content.exception;

import community.common.dto.ApiError;
import community.common.exception.CommonExceptionController;
import community.content.board_myeongjae.exception.MjArticleNotFoundException;
import community.content.myanglog.exception.PostNotFoundException;
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
      PostNotFoundException.class
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
