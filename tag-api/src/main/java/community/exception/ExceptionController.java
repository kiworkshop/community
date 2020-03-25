package community.exception;

import community.common.dto.ApiError;
import community.common.exception.CommonExceptionController;
import community.tag.exception.TagNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionController extends CommonExceptionController {
    @Override
    @ExceptionHandler(TagNotFoundException.class)
    protected ApiError handleNotFound(RuntimeException e) {
        return super.handleNotFound(e);
    }
}
