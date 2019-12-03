package community.content.simplelife.exception;

import community.common.dto.ApiError;
import community.common.exception.CommonExceptionController;
import community.content.simplelife.article.exception.SimpleArticleNotFoundException;
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
