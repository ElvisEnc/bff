package bg.com.bo.service.template.model.exceptions;

import bg.com.bo.service.template.controller.example.v1.ExampleController;
import bg.com.bo.service.template.model.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(ExampleController.class.getName());

    @ExceptionHandler(ExceptionNotFound.class)
    public ResponseEntity<ErrorResponse> handleExceptionNotFound(ExceptionNotFound exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.name(), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.name());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
