package community.content.jgraphy.api;

import community.common.dto.ApiError;
import community.common.exception.CommonExceptionController;
import community.content.jgraphy.exception.JgraphyPostNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JgraphyExceptionController extends CommonExceptionController {

  @Override
  @ExceptionHandler(JgraphyPostNotFoundException.class)
  public ApiError handleNotFound(RuntimeException e) {
    return super.handleNotFound(e);
  }

  @Override
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiError handleInvalidParam(MethodArgumentNotValidException e) {
    return super.handleInvalidParam(e);
  }
}
