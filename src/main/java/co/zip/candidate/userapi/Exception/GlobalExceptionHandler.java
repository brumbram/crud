package co.zip.candidate.userapi.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Handle unprocessable json data exception
   *
   * @param dataProcessException
   * @return a {@code ErrorResponse}
   */
  @ExceptionHandler(value = DataTransformException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleDataProcessException(
      DataTransformException dataProcessException) {
    log.error(dataProcessException.getMessage(), dataProcessException);
    return ResponseEntity.internalServerError()
        .body(
            new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                dataProcessException.getMessage()));
  }

  /**
   * Handle invalid user token exception
   *
   * @param constraintViolationException
   * @return a {@code ErrorResponse}
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException constraintViolationException) {
    log.error(constraintViolationException.getMessage(), constraintViolationException);
    return ResponseEntity.badRequest()
        .body(
            new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                constraintViolationException.getMessage()));
  }

  /**
   * Handle unknown exception
   *
   * @param unknownException
   * @return a {@code ErrorResponse}
   */
  @ExceptionHandler(value = Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleUnknownException(Throwable unknownException) {
    log.error(unknownException.getMessage(), unknownException);
    return ResponseEntity.internalServerError()
        .body(
            new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Something went wrong. Please contact support"));
  }
}
